package com.pranav.travenor.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.pranav.travenor.ui.screens.DetailsScreen
import com.pranav.travenor.ui.screens.HomeScreen
import com.pranav.travenor.ui.screens.OnBoardingScreen
import com.pranav.travenor.ui.screens.SignInScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(Routes.HomeScreen)

    Scaffold(modifier = modifier) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                when (key) {
                    is Routes.HomeScreen -> {
                        NavEntry(key) {
                            HomeScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }

                    is Routes.SignInScreen -> {
                        NavEntry(key) {
                            SignInScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }

                    is Routes.DetailsScreen -> {
                        NavEntry(key) {
                            DetailsScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }

                    is Routes.onBoardingScreen -> {
                        NavEntry(key) {
                            OnBoardingScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }

                    else -> throw IllegalArgumentException("Unknown route: $key")
                }

            }
        )
    }
}
