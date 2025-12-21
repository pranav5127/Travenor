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

    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    fun sendEmailOtp(email: String) {
        Log.d(TAG, "sendEmailOtp() called with email=$email")

        viewModelScope.launch {
            uiState = AuthUiState.Loading
            Log.d(TAG, "Sending OTP to server...")

            runCatching {
                sendOtp(email)
            }.onSuccess {
                Log.d(TAG, "OTP successfully sent to server for email=$email")
                uiState = AuthUiState.OtpSent
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to send OTP", throwable)
                uiState = AuthUiState.Error(
                    throwable.message ?: "Failed to send OTP"
                )
            }
        }
    }

    fun verifyEmailOtp(email: String, otp: String) {
        Log.d(TAG, "verifyEmailOtp() called for email=$email")

        viewModelScope.launch {
            uiState = AuthUiState.Loading
            Log.d(TAG, "Verifying OTP with server...")

            runCatching {
                verifyOtp(email, otp)
            }.onSuccess {
                Log.d(TAG, "OTP verification successful → Authenticated")
                uiState = AuthUiState.Authenticated
            }.onFailure { throwable ->
                Log.e(TAG, "OTP verification failed", throwable)
                uiState = AuthUiState.Error("Invalid OTP")
            }
        }
    }

    fun checkLogin(): Boolean {
        val loggedIn = isLoggedIn()
        Log.d(TAG, "checkLogin(): isLoggedIn=$loggedIn")
        return loggedIn
    }

    fun signOut() {
        Log.d(TAG, "signOut() called")

        viewModelScope.launch {
            logout()
            Log.d(TAG, "User signed out successfully")
            uiState = AuthUiState.Idle
        }
    }
}
