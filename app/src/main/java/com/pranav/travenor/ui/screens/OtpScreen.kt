package com.pranav.travenor.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranav.travenor.ui.components.OtpInputTextFields
import com.pranav.travenor.ui.components.ThemedButton
import com.pranav.travenor.ui.model.AuthUiState

@Composable
fun OtpScreen(
    email: String,
    uiState: AuthUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onVerifyClick: (String) -> Unit,
    onAuthenticated: () -> Unit
) {
    var otpValues by remember { mutableStateOf(List(6) { "" }) }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Authenticated) {
            onAuthenticated()
        }
    }

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
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }

        Spacer(Modifier.height(40.dp))

        Text(
            text = "OTP Verification",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Enter the code sent to $email",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        OtpInputTextFields(
            otpLength = 6,
            otpValues = otpValues,
            onUpdateOtpValuesByIndex = { index, value ->
                otpValues = otpValues.toMutableList().apply { this[index] = value }
            },
            onOtpInputComplete = {
                onVerifyClick(otpValues.joinToString(""))
            }
        )

        Spacer(Modifier.height(40.dp))

        when (uiState) {
            AuthUiState.Loading -> CircularProgressIndicator()
            is AuthUiState.Error -> Text(
                text = uiState.message,
                color = Color.Red
            )
            else -> Unit
        }

        Spacer(Modifier.height(24.dp))

        ThemedButton(
            text = "Verify",
            onClick = {
                onVerifyClick(otpValues.joinToString(""))
            }
        )
    }
}
