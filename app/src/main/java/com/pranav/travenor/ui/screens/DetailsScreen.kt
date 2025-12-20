package com.pranav.travenor.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranav.travenor.R
import com.pranav.travenor.ui.components.ThemedButton
import androidx.compose.ui.geometry.Rect

@Composable
fun DetailsScreen(onBackClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp),
            contentScale = ContentScale.Crop
        )

        DetailsTopBar(onBackClick)
        BottomDetailsSheet()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp),
        title = {
            Text("Details", color = Color.White, fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Black.copy(alpha = 0.25f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun BottomDetailsSheet() {
    val collapsedOffset = 400.dp
    val expandedOffset = 120.dp

    var expanded by rememberSaveable { mutableStateOf(false) }

    val offset by animateDpAsState(
        targetValue = if (expanded) expandedOffset else collapsedOffset,
        label = "sheet_offset"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = offset)
            .drawWithContent {
                val arcHeight = 10.dp.toPx()
                val cornerRadius = 26.dp.toPx()
                val flatWidth = size.width * 0.055f

                val path = Path().apply {
                    moveTo(0f, arcHeight + cornerRadius)

                    arcTo(
                        rect = Rect(
                            left = 0f,
                            top = arcHeight,
                            right = cornerRadius * 2,
                            bottom = arcHeight + cornerRadius * 2
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    lineTo(flatWidth, arcHeight)

                    cubicTo(
                        flatWidth + size.width * 0.1f, -arcHeight,
                        size.width - flatWidth - size.width * 0.1f, -arcHeight,
                        size.width - flatWidth, arcHeight
                    )

                    lineTo(size.width - cornerRadius, arcHeight)

                    arcTo(
                        rect = Rect(
                            left = size.width - cornerRadius * 2,
                            top = arcHeight,
                            right = size.width,
                            bottom = arcHeight + cornerRadius * 2
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(path, Color.White)
                drawContent()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount < -10) expanded = true
                        else if (dragAmount > 10) expanded = false
                    }
                }
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 120.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(40.dp)
                    .height(4.dp)
                    .background(Color.LightGray, RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))
            DestinationHeader()
            Spacer(modifier = Modifier.height(20.dp))
            DestinationStats()
            Spacer(modifier = Modifier.height(20.dp))
            GallerySection()
            Spacer(modifier = Modifier.height(24.dp))

            AboutDestination(
                isExpanded = expanded,
                onToggle = { expanded = !expanded }
            )

            Spacer(modifier = Modifier.height(48.dp))

            ThemedButton(text = "Book Now", onClick = {})
        }
    }
}

@Composable
fun DestinationHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Kolkata Reservoir", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("Kolkata, India", color = Color.Gray)
        }
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DestinationStats() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, null, tint = Color.Gray, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Kolkata", color = Color.Gray)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("4.7", fontWeight = FontWeight.Bold)
            Text(" (2498)", color = Color.Gray)
        }

        Text(
            buildAnnotatedString {
                withStyle(
                    androidx.compose.ui.text.SpanStyle(
                        color = colorResource(id = R.color.travenor_blue),
                        fontWeight = FontWeight.Bold
                    )
                ) { append("$59") }
                append("/Person")
            }
        )
    }
}

@Composable
fun GallerySection() {

    val images = listOf(
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(images.size) { index ->
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {

                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (index == images.lastIndex) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.55f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "16+",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun AboutDestination(isExpanded: Boolean, onToggle: () -> Unit) {
    Column {
        Text("About Destination", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text =
                "You will get a complete travel package on the beaches. " +
                        "Packages include airline tickets, hotels, transportation, " +
                        "sightseeing, and much more for a wonderful experience.",
            color = Color.Gray,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            maxLines = if (isExpanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (isExpanded) "Read Less" else "Read More",
            color = Color(0xFFFF7029),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onToggle() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen()
}
