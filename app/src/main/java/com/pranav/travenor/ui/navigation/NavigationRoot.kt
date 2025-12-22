package com.pranav.travenor.ui.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.pranav.travenor.ui.screens.*
import com.pranav.travenor.ui.viewmodel.AuthViewModel
import com.pranav.travenor.ui.viewmodel.HomeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun NavigationRoot(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = koinViewModel()
    val prefs = remember {
        context.getSharedPreferences("travenor_prefs", Context.MODE_PRIVATE)
    }

    Scaffold(modifier = modifier) { innerPadding ->

        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { route ->

                when (route) {

                    is Routes.HomeScreen -> NavEntry(route) {
                        HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                        )
                    }

                    is Routes.SignInScreen -> NavEntry(route) {
                        SignInScreen(
                            modifier = Modifier.padding(innerPadding),
                            onSignInClick = { email ->
                                authViewModel.sendEmailOtp(email)
                                backStack.add(Routes.OtpScreen(email))
                            }
                        )
                    }

                    is Routes.OtpScreen -> NavEntry(route) {
                        OtpScreen(
                            email = route.email,
                            uiState = authViewModel.uiState,
                            modifier = Modifier.padding(innerPadding),
                            onBackClick = {
                                backStack.removeLast()
                            },
                            onVerifyClick = { otp ->
                                authViewModel.verifyEmailOtp(route.email, otp)
                            },
                            onAuthenticated = {
                                backStack.clear()
                                backStack.add(Routes.HomeScreen)
                            }
                        )
                    }

                    is Routes.OnBoardingScreen -> NavEntry(route) {
                        OnBoardingScreen(
                            modifier = Modifier.padding(innerPadding),
                            onGetStartedClick = {
                                prefs.edit {
                                    putBoolean("is_first_launch", false)
                                }
                                backStack.clear()
                                backStack.add(Routes.SignInScreen)
                            }
                        )
                    }

                    is Routes.DetailsScreen -> NavEntry(route) {
                        DetailsScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    else -> error("Unknown route: $route")
                }
            }
        )
    }
}
