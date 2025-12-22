package com.pranav.travenor.domain.usecase

import com.pranav.travenor.data.model.Destination
import com.pranav.travenor.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow

class ObserveDestinationsUseCase(
    private val repository: DbRepository
) {
     operator fun invoke(): Flow<List<Destination>> {
         return repository.observeDestinations()
     }
}