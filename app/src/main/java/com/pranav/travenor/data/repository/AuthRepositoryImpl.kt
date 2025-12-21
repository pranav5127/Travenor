package com.pranav.travenor.data.repository

import com.pranav.travenor.data.datasources.SupabaseAuthDataSource
import com.pranav.travenor.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: SupabaseAuthDataSource
) : AuthRepository {
    override suspend fun sendOtp(email: String) {
        dataSource.sendOtp(email)
    }

    override suspend fun verifyOtp(email: String, otp: String) {
        dataSource.verifyOtp(email, otp)
    }

    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override suspend fun logout() {
        dataSource.logout()
    }

}