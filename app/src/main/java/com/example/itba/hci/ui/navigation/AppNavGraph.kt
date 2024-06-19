package com.example.itba.hci.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.itba.hci.ui.screens.DevicesScreen
import com.example.itba.hci.ui.screens.HomeScreen
import com.example.itba.hci.ui.screens.RoutinesScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route
    ) {
        composable(AppDestinations.DEVICES.route) {
            DevicesScreen()
        }
        composable(AppDestinations.HOME.route) {
            HomeScreen()
        }
        composable(AppDestinations.ROUTINES.route) {
            RoutinesScreen()
        }
    }
}