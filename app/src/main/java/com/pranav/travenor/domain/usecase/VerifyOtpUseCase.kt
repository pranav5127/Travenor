package com.pranav.travenor.domain.usecase

import com.pranav.travenor.domain.repository.AuthRepository

class VerifyOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, otp: String) {
        repository.verifyOtp(email, otp)
    }
}


