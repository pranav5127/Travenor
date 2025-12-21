package com.pranav.travenor.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object onBoardingScreen : Routes, NavKey

    @Serializable
    data object DetailsScreen: Routes, NavKey

    @Serializable
    data object SignInScreen: Routes, NavKey

    @Serializable
    data object HomeScreen: Routes, NavKey


}