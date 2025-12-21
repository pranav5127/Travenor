package com.pranav.travenor.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranav.travenor.domain.usecase.IsLoggedInUseCase
import com.pranav.travenor.domain.usecase.LogoutUseCase
import com.pranav.travenor.domain.usecase.SendOtpUseCase
import com.pranav.travenor.domain.usecase.VerifyOtpUseCase
import com.pranav.travenor.ui.model.AuthUiState
import kotlinx.coroutines.launch

private const val TAG = "AuthViewModel"

class AuthViewModel(
    private val sendOtp: SendOtpUseCase,
    private val verifyOtp: VerifyOtpUseCase,
    private val isLoggedIn: IsLoggedInUseCase,
    private val logout: LogoutUseCase
) : ViewModel() {

    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Initializing)
        private set

    init {
        restoreAuthState()
    }

    private fun restoreAuthState() {
        viewModelScope.launch {
            val loggedIn = isLoggedIn()

            uiState =
                if (loggedIn) {
                    AuthUiState.Authenticated
                } else {
                    AuthUiState.Idle
                }
        }
    }

    fun sendEmailOtp(email: String) {
        Log.d(TAG, "sendEmailOtp() called with email=$email")

        viewModelScope.launch {
            uiState = AuthUiState.Loading

            runCatching {
                sendOtp(email)
            }.onSuccess {
                uiState = AuthUiState.OtpSent
            }.onFailure { throwable ->
                uiState = AuthUiState.Error(
                    throwable.message ?: "Failed to send OTP"
                )
            }
        }
    }

    fun verifyEmailOtp(email: String, otp: String) {
        Log.d(TAG, "verifyEmailOtp() called")

        viewModelScope.launch {
            uiState = AuthUiState.Loading

            runCatching {
                verifyOtp(email, otp)
            }.onSuccess {
                uiState = AuthUiState.Authenticated
            }.onFailure {
                uiState = AuthUiState.Error("Invalid OTP")
            }
        }
    }

    fun signOut() {
        Log.d(TAG, "signOut() called")

        viewModelScope.launch {
            logout()
            uiState = AuthUiState.Idle
        }
    }
}
