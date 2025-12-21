package com.pranav.travenor.domain.usecase

import com.pranav.travenor.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}