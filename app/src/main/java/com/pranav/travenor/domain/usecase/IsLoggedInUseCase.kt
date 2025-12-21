package com.pranav.travenor.domain.usecase

import com.pranav.travenor.domain.repository.AuthRepository

class IsLoggedInUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean = repository.isLoggedIn()
}