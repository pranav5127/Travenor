package com.pranav.travenor.domain.usecase

import com.pranav.travenor.domain.repository.AuthRepository

class IsLoggedInUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.isLoggedIn()
    }
}
