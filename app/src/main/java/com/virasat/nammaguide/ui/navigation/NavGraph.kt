package com.virasat.nammaguide.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.virasat.nammaguide.ui.screens.*

sealed class Screen(val route: String) {
    object Splash     : Screen("splash")
    object Discovery  : Screen("discovery")
    object SiteDetail : Screen("site_detail/{siteId}") {
        fun createRoute(siteId: String) = "site_detail/$siteId"
    }
    object QrScanner  : Screen("qr_scanner")
    object Passport   : Screen("passport")
    object Dashboard  : Screen("dashboard")
    object Settings   : Screen("settings")
    object Favorites  : Screen("favorites")
}

@Composable
fun VirastatNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                navController.navigate(Screen.Discovery.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Discovery.route) {
            DiscoveryScreen(
                onSiteClick   = { siteId -> navController.navigate(Screen.SiteDetail.createRoute(siteId)) },
                onScanClick   = { navController.navigate(Screen.QrScanner.route) },
                onPassportClick = { navController.navigate(Screen.Passport.route) },
                onDashboardClick = { navController.navigate(Screen.Dashboard.route) }
            )
        }

        composable(
            route = Screen.SiteDetail.route,
            arguments = listOf(navArgument("siteId") { type = NavType.StringType })
        ) { backStack ->
            val siteId = backStack.arguments?.getString("siteId") ?: return@composable
            SiteDetailScreen(
                siteId = siteId,
                onBack = { navController.popBackStack() },
                onScanQr = { navController.navigate(Screen.QrScanner.route) }
            )
        }

        composable(Screen.QrScanner.route) {
            QrScannerScreen(
                onSiteFound = { siteId ->
                    navController.navigate(Screen.SiteDetail.createRoute(siteId)) {
                        popUpTo(Screen.QrScanner.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Passport.route) {
            PassportScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onBack = { navController.popBackStack() },
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                onPassportClick = { navController.navigate(Screen.Passport.route) }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onBack = { navController.popBackStack() },
                onSiteClick = { siteId -> navController.navigate(Screen.SiteDetail.createRoute(siteId)) }
            )
        }
    }
}
