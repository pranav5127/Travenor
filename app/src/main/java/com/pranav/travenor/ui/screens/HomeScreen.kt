package com.pranav.travenor.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranav.travenor.R
import com.pranav.travenor.ui.components.DestinationCard
import com.pranav.travenor.ui.theme.GillSansMtFont

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F9))
            .padding(top = 16.dp)
    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(24.dp))
        TitleSection()
        Spacer(modifier = Modifier.height(24.dp))
        BestDestinationSection()
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(4.dp)
                .padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFE0B2))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = stringResource(R.string.user),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.user_name),
                fontFamily = GillSansMtFont,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun TitleSection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        ExploreText()
        BeautifulWorldText()
    }
}

@Composable
fun ExploreText() {
    Text(
        text = stringResource(R.string.explore_the),
        fontSize = 38.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 44.sp,
        color = Color.Black
    )
}

@Composable
fun BeautifulWorldText() {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    Text(
        text = buildAnnotatedString {
            append("Beautiful ")
            withStyle(style = SpanStyle(color = Color(0xFFFF7029))) {
                append("world!")
            }
        },
        fontSize = 38.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 44.sp,
        color = Color.Black,
        onTextLayout = { textLayoutResult = it },
        modifier = Modifier.drawBehind {
            textLayoutResult?.let { layoutResult ->
                val text = layoutResult.layoutInput.text.text
                val targetWord = "world!"
                val startIndex = text.lastIndexOf(targetWord)
                if (startIndex >= 0) {
                    val endIndex = startIndex + targetWord.length
                    val startBounds = layoutResult.getBoundingBox(startIndex)
                    val endBounds = layoutResult.getBoundingBox(endIndex - 1)

                    val x1 = startBounds.left
                    val x2 = endBounds.right
                    val y = startBounds.bottom

                    val path = Path().apply {
                        moveTo(x1, y + 25f)
                        quadraticTo(
                            (x1 + x2) / 2,
                            y + 5f,
                            x2 + 10f,
                            y + 20f
                        )
                    }

                    drawPath(
                        path = path,
                        color = Color(0xFFFF7029),
                        style = Stroke(
                            width = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun BestDestinationSection() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.best_destination),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(destinations) { destination ->
                DestinationCard(destination)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

data class Destination(
    val name: String,
    val location: String,
    val rating: String,
    val imageRes: Int
)

val destinations = listOf(
    Destination("Kolkata Reservoir", "Kolkata, India", "4.7", R.drawable.home),
    Destination("Zurich", "Switzerland", "4.8", R.drawable.home),
    Destination("Paris", "France", "4.6", R.drawable.home)
)
