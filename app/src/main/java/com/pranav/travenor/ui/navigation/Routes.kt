package com.pranav.travenor.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes : NavKey {

    @Serializable
    data object OnBoardingScreen : Routes

    @Serializable
    data object SignInScreen : Routes

    @Serializable
    data class OtpScreen(
        val email: String
    ) : Routes

    @Serializable
    data object HomeScreen : Routes

    @Serializable
    data class DetailsScreen(
        val destinationId: String
    ) : Routes
}
