package com.pranav.travenor.ui.model

sealed interface DbUiState {
    data object Initializing : DbUiState
    data object Idle : DbUiState
    data class Error(val message: String): DbUiState
}