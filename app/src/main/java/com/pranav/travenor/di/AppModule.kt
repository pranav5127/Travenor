package com.pranav.travenor.di

import com.pranav.travenor.data.datasources.SupabaseAuthDataSource
import com.pranav.travenor.data.repository.AuthRepositoryImpl
import com.pranav.travenor.domain.repository.AuthRepository
import com.pranav.travenor.domain.usecase.IsLoggedInUseCase
import com.pranav.travenor.domain.usecase.LogoutUseCase
import com.pranav.travenor.domain.usecase.SendOtpUseCase
import com.pranav.travenor.domain.usecase.VerifyOtpUseCase
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import org.koin.dsl.module
import com.pranav.travenor.BuildConfig
import com.pranav.travenor.ui.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModel


val appModule = module {

    // Supabase Client
    single {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY,
        ) {
            install(Auth)

        }
    }

    // Data source
    single { SupabaseAuthDataSource(get()) }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    // Use cases
    factory { SendOtpUseCase(get()) }
    factory { VerifyOtpUseCase(get()) }
    factory { IsLoggedInUseCase(get()) }
    factory { LogoutUseCase(get()) }

   // View model
   viewModel { AuthViewModel(get(), get(), get(), get()) }

}
