package com.pranav.travenor.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranav.travenor.ui.components.OtpInputTextFields
import com.pranav.travenor.ui.components.ThemedButton
import com.pranav.travenor.ui.model.AuthUiState
import com.pranav.travenor.ui.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OtpScreen(
    email: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onVerifyClick: (String) -> Unit,
    onAuthenticated: () -> Unit
) {
    val viewModel: AuthViewModel = koinViewModel()
    val state = viewModel.uiState

    var otpValues by remember { mutableStateOf(List(6) { "" }) }

    LaunchedEffect(state) {
        if (state is AuthUiState.Authenticated) {
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
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
        }

        Spacer(Modifier.height(40.dp))

        Text("OTP Verification", fontSize = 26.sp, fontWeight = FontWeight.Bold)

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
            onUpdateOtpValuesByIndex = { i, v ->
                otpValues = otpValues.toMutableList().apply { this[i] = v }
            },
            onOtpInputComplete =  {
                onVerifyClick(otpValues.joinToString(""))
            },
            modifier = Modifier,
        )

        Spacer(Modifier.height(40.dp))

        when (state) {
            AuthUiState.Loading -> CircularProgressIndicator()
            is AuthUiState.Error -> Text(state.message, color = Color.Red)
            else -> Unit
        }

        ThemedButton(
            text = "Verify",
            onClick = {
                onVerifyClick(otpValues.joinToString(""))
            }
        )
    }
}
