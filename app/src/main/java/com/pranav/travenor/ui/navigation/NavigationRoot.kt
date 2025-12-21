package com.pranav.travenor.ui.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.core.content.edit
import com.pranav.travenor.ui.screens.*
import com.pranav.travenor.ui.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = koinViewModel()

    val sharedPreferences = remember {
        context.getSharedPreferences("travenor_prefs", Context.MODE_PRIVATE)
    }

    val isFirstLaunch = remember {
        sharedPreferences.getBoolean("is_first_launch", true)
    }

    val isLoggedIn = remember {
        authViewModel.checkLogin()
    }

    val initialRoute = when {
        isFirstLaunch -> Routes.OnBoardingScreen
        isLoggedIn -> Routes.HomeScreen
        else -> Routes.SignInScreen
    }

    val backStack = rememberNavBackStack(initialRoute)

    Scaffold(modifier = modifier) { innerPadding ->

        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->

                when (key) {

                    is Routes.HomeScreen -> NavEntry(key) {
                        HomeScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    is Routes.SignInScreen -> NavEntry(key) {
                        SignInScreen(
                            modifier = Modifier.padding(innerPadding),
                            onSignInClick = { email ->
                                authViewModel.sendEmailOtp(email)
                                backStack.add(Routes.OtpScreen(email))
                            }
                        )
                    }

                    is Routes.OtpScreen -> NavEntry(key) {
                        OtpScreen(
                            email = key.email,
                            modifier = Modifier.padding(innerPadding),
                            onBackClick = {
                                backStack.removeLast()
                            },
                            onVerifyClick = { otp ->
                                authViewModel.verifyEmailOtp(key.email, otp)
                            },
                            onAuthenticated = {
                                backStack.clear()
                                backStack.add(Routes.HomeScreen)
                            }
                        )
                    }

                    is Routes.OnBoardingScreen -> NavEntry(key) {
                        OnBoardingScreen(
                            modifier = Modifier.padding(innerPadding),
                            onGetStartedClick = {
                                sharedPreferences.edit {
                                    putBoolean("is_first_launch", false)
                                }
                                backStack.clear()
                                backStack.add(Routes.SignInScreen)
                            }
                        )
                    }

                    is Routes.DetailsScreen -> NavEntry(key) {
                        DetailsScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    else -> error("Unknown route")
                }
            }
        )
    }
}
