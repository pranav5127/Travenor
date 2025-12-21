package com.pranav.travenor

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.pranav.travenor.ui.model.AuthUiState
import com.pranav.travenor.ui.navigation.NavigationRoot
import com.pranav.travenor.ui.navigation.Routes
import com.pranav.travenor.ui.theme.TravenorTheme
import com.pranav.travenor.ui.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val authViewModel: AuthViewModel = koinViewModel()
            val authState = authViewModel.uiState

            splash.setKeepOnScreenCondition {
                authState is AuthUiState.Initializing
            }

            if (authState !is AuthUiState.Initializing) {

                val startDestination: NavKey =
                    if (authState is AuthUiState.Authenticated)
                        Routes.HomeScreen
                    else
                        Routes.SignInScreen

                val backStack = rememberNavBackStack(startDestination)

                TravenorTheme {
                    NavigationRoot(
                        backStack = backStack
                    )
                }
            }
        }
    }
}
