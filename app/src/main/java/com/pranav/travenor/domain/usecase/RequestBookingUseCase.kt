package com.pranav.travenor.domain.usecase

import com.pranav.travenor.domain.repository.DbRepository

class RequestBookingUseCase(
    private val repository: DbRepository
) {
    suspend operator fun invoke(id: String) {
        repository.updateBookingState(id)
    }
}
