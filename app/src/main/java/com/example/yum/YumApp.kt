package com.example.yum

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.yum.common.component.YumBottomBar
import com.example.yum.common.snackbar.SnackbarManager
import com.example.yum.screens.cart.CartScreen
import com.example.yum.screens.feed.FeedScreen
import com.example.yum.screens.search.SearchScreen
import com.example.yum.screens.sign_in.SignInScreen
import com.example.yum.screens.sign_up.SignUpScreen
import com.example.yum.screens.splash.SplashScreen
import com.example.yum.screens.user.UserScreen
import com.example.yum.ui.theme.YumTheme
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YumApp() {
    val appState = rememberYumAppState()
//    val snackbarHostState = remember { SnackbarHostState() }
    YumTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                topBar = {

                }, //TODO
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        YumBottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::clearAndNavigate,
                        )
                    }
                },
                snackbarHost = { SnackbarHost(appState.snackbarHostState) },
            ) { paddingValues ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(paddingValues),
                ) {
                    yumGraph(appState)
                }

            }
        }

    }
}


fun a() {

}

// app state
@Composable
fun rememberYumAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
) = remember(navController, coroutineScope, snackbarManager) {
    YumAppState(navController, snackbarHostState, snackbarManager, resources, coroutineScope)
}


// app graph
fun NavGraphBuilder.yumGraph(appState: YumAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(
            openAndPopUp = { route, popup ->
                appState.navigateAndPopUp(route, popup)
            },
        )
    }
    navigation(
        route = HOME_SCREEN,
        startDestination = HomeScreenSection.FEED.route,
    ) {
        composable(HomeScreenSection.FEED.route) {
            FeedScreen(
                onRecipeTap = {
                    appState.navigate(SIGNUP_SCREEN)
                },
            )
        }
        composable(HomeScreenSection.SEARCH.route) {
            SearchScreen(onRecipeClick = {})
        }
        composable(HomeScreenSection.CART.route) {
            CartScreen(onRecipeTap = {})
        }
        composable(HomeScreenSection.USER.route) {
            UserScreen(
                onOpenScreen = { route -> appState.navigate(route) },
                restartApp = { route -> appState.clearAndNavigate(route) }
            )
        }

    }
    composable(SIGNIN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popup -> appState.navigateAndPopUp(route, popup) })
    }
    composable(SIGNUP_SCREEN) {
        SignUpScreen(
            onBack = appState::popUp,
        )
    }

    composable(
        route = "$RECIPE_SCREEN$RECIPE_ID_ARG",
        arguments = listOf(
            navArgument(RECIPE_ID) {
                defaultValue = RECIPE_DEFAULT_ID
            },
        ),
    ) {
        // recipe screen
    }
}


@Preview
@Composable
fun YumAppPreView() {
    YumApp()
}
