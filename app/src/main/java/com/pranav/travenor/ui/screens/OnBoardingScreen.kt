package com.pranav.travenor.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranav.travenor.R
import com.pranav.travenor.ui.theme.GeometrFont
import com.pranav.travenor.ui.theme.GillSansMtFont

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.on_board_img),
                contentDescription = "Onboarding Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "Skip",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 48.dp, end = 24.dp)
                    .clickable { }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

            Text(
                text = buildAnnotatedString {
                    append("Life is short and the\nworld is ")
                    withStyle(style = SpanStyle(color = Color(0xFFFF7029))) {
                        append("wide")
                    }
                },
                fontFamily = GeometrFont,
                fontSize = 30.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp,
                color = Color.Black,
                onTextLayout = { textLayoutResult = it },
                modifier = Modifier.drawBehind {
                    textLayoutResult?.let { layoutResult ->
                        val text = layoutResult.layoutInput.text.text
                        val startIndex = text.lastIndexOf("wide")
                        if (startIndex >= 0) {
                            val endIndex = startIndex + "wide".length
                            val startBounds = layoutResult.getBoundingBox(startIndex)
                            val endBounds = layoutResult.getBoundingBox(endIndex - 1)

                            val x1 = startBounds.left
                            val x2 = endBounds.right
                            val y = startBounds.bottom

                            val path = Path().apply {
                                moveTo(x1 - 10f, y + 25f)
                                quadraticTo(
                                    (x1 + x2) / 2,
                                    y + 5f,
                                    x2 + 20f,
                                    y + 25f
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "At Friends tours and travel, we customize reliable and trustworthy educational tours to destinations all over the world",
                fontFamily = GillSansMtFont,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0D6EFD)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen()
}
