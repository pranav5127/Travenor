package com.pranav.travenor.domain.usecase

import com.pranav.travenor.data.model.Destination
import com.pranav.travenor.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow

class ObserveDestinationDetailsUseCase(
    private val repository: DbRepository
){

    operator fun invoke(id: String): Flow<Destination?> {
        return repository.observeDestinationDetails(id)
    }

}