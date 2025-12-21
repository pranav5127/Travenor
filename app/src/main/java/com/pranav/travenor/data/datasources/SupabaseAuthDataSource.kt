package com.pranav.travenor.data.datasources

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP

class SupabaseAuthDataSource(
    private val supabase: SupabaseClient
) {

    suspend fun sendOtp(email: String) {
       supabase.auth.signInWith(OTP) {
           this.email = email
       }
    }

    suspend fun verifyOtp(email: String, otp: String) {
        supabase.auth.verifyEmailOtp(
            type = OtpType.Email.EMAIL,
            email = email,
            token = otp
        )
    }

    suspend fun isLoggedIn(): Boolean {
        supabase.auth.awaitInitialization()
        return supabase.auth.currentSessionOrNull() != null
    }


    suspend fun logout() {
        supabase.auth.signOut()
    }
}