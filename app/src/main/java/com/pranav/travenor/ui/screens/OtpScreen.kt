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
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun OtpScreen(
    email: String,
    uiState: AuthUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onVerifyClick: (String) -> Unit,
    onResendClick: () -> Unit,
    onAuthenticated: () -> Unit
) {
    var otpValues by remember { mutableStateOf(List(6) { "" }) }
    var timeLeft by remember { mutableIntStateOf(86) }
    var isTimerRunning by remember { mutableStateOf(true) }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Authenticated) onAuthenticated()
    }

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            isTimerRunning = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(16.dp))

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.Start)
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3F3F5))
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }

        Spacer(Modifier.height(56.dp))

        Text(
            text = "OTP Verification",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F1F24)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Please check your email $email\nto see the verification code",
            textAlign = TextAlign.Center,
            color = Color(0xFF8A8A8E),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )

        Spacer(Modifier.height(36.dp))

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

        Spacer(Modifier.height(36.dp))

        ThemedButton(
            text = "Verify",
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                onVerifyClick(otpValues.joinToString(""))
            }
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Resend code to",
                color = Color(0xFF8A8A8E),
                fontSize = 14.sp
            )

            if (isTimerRunning) {
                val minutes = timeLeft / 60
                val seconds = timeLeft % 60
                Text(
                    text = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds),
                    color = Color(0xFF8A8A8E),
                    fontSize = 14.sp
                )
            } else {
                TextButton(
                    onClick = {
                        onResendClick()
                        timeLeft = 86
                        isTimerRunning = true
                    }
                ) {
                    Text(
                        text = "Resend",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        when (uiState) {
            AuthUiState.Loading -> CircularProgressIndicator()
            is AuthUiState.Error -> Text(
                text = uiState.message,
                color = Color.Red,
                textAlign = TextAlign.Center
            )

            else -> Unit
        }
    }
}
