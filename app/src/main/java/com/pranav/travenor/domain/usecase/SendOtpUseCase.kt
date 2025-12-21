package com.pranav.travenor.domain.usecase

import com.pranav.travenor.domain.repository.AuthRepository

class SendOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) {
        repository.sendOtp(email)
    }
}