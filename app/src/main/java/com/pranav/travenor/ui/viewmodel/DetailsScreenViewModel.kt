package com.pranav.travenor.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranav.travenor.domain.usecase.ObserveDestinationDetailsUseCase
import com.pranav.travenor.ui.model.DbUiState
import com.pranav.travenor.ui.model.UiDestinationDetails
import com.pranav.travenor.ui.utils.toDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val observeDestinationDetails: ObserveDestinationDetailsUseCase
) : ViewModel() {

    var uiState = mutableStateOf<DbUiState>(DbUiState.Initializing)
        private set

    private val _details = MutableStateFlow<UiDestinationDetails?>(null)
    val details = _details.asStateFlow()

    fun subscribe(id: String) {
        viewModelScope.launch {
            runCatching {
                observeDestinationDetails(id).collectLatest { destination ->
                    _details.value = destination?.toDetailsUi()
                    uiState.value = DbUiState.Idle
                }
            }.onFailure { e ->
                uiState.value = DbUiState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }
}




