package com.pranav.travenor.domain.repository

interface AuthRepository {
    suspend fun sendOtp(email: String)
    suspend fun verifyOtp(email: String, otp: String)
    fun isLoggedIn(): Boolean
    suspend fun logout()
}