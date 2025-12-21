package com.pranav.travenor.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranav.travenor.ui.components.ThemedButton

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSignInClick: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.Start)
                .clip(CircleShape)
                .background(Color(0xFFF7F7F9))
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, null)
        }

        Spacer(Modifier.height(100.dp))

        Text("Sign in now", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(12.dp))

        Text(
            "Please sign in to continue",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF7F7F9),
                unfocusedContainerColor = Color(0xFFF7F7F9),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(24.dp))

        ThemedButton(
            text = "Sign in",
            onClick = { onSignInClick(email) }
        )
    }
}
