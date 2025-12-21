package com.pranav.travenor.ui.model

sealed interface AuthUiState {
    data object Idle: AuthUiState
    data object Loading: AuthUiState
    data object OtpSent: AuthUiState
    data object Authenticated: AuthUiState
    data class Error(val message: String): AuthUiState
}