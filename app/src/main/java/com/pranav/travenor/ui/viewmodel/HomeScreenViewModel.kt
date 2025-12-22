package com.pranav.travenor.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranav.travenor.domain.usecase.ObserveDestinationsUseCase
import com.pranav.travenor.ui.model.DbUiState
import com.pranav.travenor.ui.model.UiDestination
import com.pranav.travenor.ui.utils.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val observeDestinations: ObserveDestinationsUseCase,
) : ViewModel() {

    var uiState by mutableStateOf<DbUiState>(value = DbUiState.Initializing)
        private set

    private val _destinations = MutableStateFlow<List<UiDestination>>(emptyList())
    val destinations = _destinations.asStateFlow()

    init{
       subscribeToDestinations()

    }

    private fun subscribeToDestinations() {
        viewModelScope.launch {
            runCatching {
                observeDestinations().collectLatest { list ->
                    _destinations.value = list.map { it.toUi() }
                    uiState = DbUiState.Idle
                }
            }.onFailure { e ->
                uiState = DbUiState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }


}
