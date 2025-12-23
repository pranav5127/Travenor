package com.pranav.travenor.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranav.travenor.domain.usecase.RequestBookingUseCase
import com.pranav.travenor.domain.usecase.ObserveDestinationDetailsUseCase
import com.pranav.travenor.ui.model.DbUiState
import com.pranav.travenor.ui.model.UiDestinationDetails
import com.pranav.travenor.ui.utils.toDetailsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val observeDestinationDetails: ObserveDestinationDetailsUseCase,
    private val bookDestination: RequestBookingUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "DetailsVM"
    }

    var uiState = mutableStateOf<DbUiState>(DbUiState.Initializing)
        private set

    private val _details = MutableStateFlow<UiDestinationDetails?>(null)
    val details = _details.asStateFlow()

    fun subscribe(id: String) {
        Log.d(TAG, "subscribe() called with id=$id")

        viewModelScope.launch {
            runCatching {
                observeDestinationDetails(id).collectLatest { destination ->
                    Log.d(
                        TAG,
                        "Destination update received: ${destination?.id}, bookingState=${destination?.bookingState}"
                    )

                    _details.value = destination?.toDetailsUi()
                    uiState.value = DbUiState.Idle
                }
            }.onFailure { e ->
                Log.e(TAG, "Error while observing destination", e)
                uiState.value = DbUiState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }

    fun bookNow(id: String) {
        Log.d(TAG, "bookNow() clicked for id=$id")

        viewModelScope.launch {
            runCatching {
                bookDestination(id)
                Log.d(TAG, "bookDestination() SUCCESS for id=$id")
            }.onFailure { e ->
                Log.e(TAG, "bookDestination() FAILED for id=$id", e)
            }
        }
    }
}
