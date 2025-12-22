package com.pranav.travenor.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.pranav.travenor.ui.components.ThemedButton
import com.pranav.travenor.ui.model.DbUiState
import com.pranav.travenor.ui.model.UiDestinationDetails
import com.pranav.travenor.ui.viewmodel.DetailsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsScreen(
    destinationId: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val viewModel: DetailsScreenViewModel = koinViewModel()

    LaunchedEffect(destinationId) {
        viewModel.subscribe(destinationId)
    }

    val uiState by viewModel.uiState
    val details by viewModel.details.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            DbUiState.Initializing -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is DbUiState.Error -> Text(
                text = (uiState as DbUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
            DbUiState.Idle -> details?.let {
                DetailsContent(it, onBackClick)
            }
        }
    }
}

@Composable
private fun DetailsContent(
    data: UiDestinationDetails,
    onBackClick: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {

        AsyncImage(
            model = data.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(480.dp),
            contentScale = ContentScale.Crop
        )

        DetailsTopBar(onBackClick)

        BottomDetailsSheet(data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("Details", color = Color.White, fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp).background(Color.Black.copy(0.25f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    )
}

@Composable
fun BottomDetailsSheet(data: UiDestinationDetails) {
    val collapsed = 420.dp
    val expanded = 120.dp
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val offset by animateDpAsState(
        if (isExpanded) expanded else collapsed,
        label = "sheet"
    )

    Box(
        Modifier
            .fillMaxWidth()
            .offset(y = offset)
            .drawWithContent {
                val r = 28.dp.toPx()
                val a = 10.dp.toPx()
                val w = size.width

                val path = Path().apply {
                    moveTo(0f, r + a)
                    arcTo(Rect(0f, a, r * 2, a + r * 2), 180f, 90f, false)
                    cubicTo(w * .25f, -a, w * .75f, -a, w - r, a)
                    arcTo(Rect(w - r * 2, a, w, a + r * 2), 270f, 90f, false)
                    lineTo(w, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(path, Color.White)
                drawContent()
            }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, d ->
                        if (d < -12) isExpanded = true
                        if (d > 12) isExpanded = false
                    }
                }
        ) {
            Box(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 40.dp, height = 4.dp)
                    .background(Color.LightGray, RoundedCornerShape(2.dp))
            )

            Spacer(Modifier.height(20.dp))

            HeaderSection(data)

            Spacer(Modifier.height(20.dp))

            StatsRow(data)

            Spacer(Modifier.height(20.dp))

            GalleryRow(data.galleryImages)

            Spacer(Modifier.height(24.dp))

            AboutSection(data.about, isExpanded) { isExpanded = !isExpanded }

            ThemedButton(modifier = Modifier.padding(top = 8.dp, bottom = 48.dp), "Book Now", onClick = {})
        }
    }
}

@Composable
fun HeaderSection(data: UiDestinationDetails) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(data.title, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(data.location, color = Color.Gray)
        }
        Box(
            Modifier.size(44.dp).background(Color(0xFFDDF2E1), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("👤")
        }
    }
}

@Composable
fun StatsRow(data: UiDestinationDetails) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(data.location, color = Color.Gray)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(4.dp))
            Text("${data.rating} (2498)", fontWeight = FontWeight.Medium)
        }

        Text(
            "$${data.price}/Person",
            color = Color(0xFF4C6FFF),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GalleryRow(images: List<String>?) {
    if (images.isNullOrEmpty()) return

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        itemsIndexed(images.take(5)) { index, img ->
            Box {
                AsyncImage(
                    model = img,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                if (index == 4 && images.size > 5) {
                    Box(
                        Modifier
                            .matchParentSize()
                            .background(Color.Black.copy(0.45f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+${images.size - 4}", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun AboutSection(text: String?, expanded: Boolean, onToggle: () -> Unit) {
    Column {
        Text("About Destination", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(
            text ?: "",
            color = Color.Gray,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 22.sp
        )
        Spacer(Modifier.height(6.dp))
        Text(
            if (expanded) "Read Less" else "Read More",
            color = Color(0xFFFF6F2D),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onToggle() }
        )
    }
}
