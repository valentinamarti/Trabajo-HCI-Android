package com.example.itba.hci.utils.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.itba.hci.ui.screens.DevicesScreen
import com.example.itba.hci.ui.screens.HomeScreen
import com.example.itba.hci.ui.screens.RoutinesScreen

@Composable
fun AppNavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(AppDestinations.DEVICES.route) {
            DevicesScreen(navController = navController)
        }
        composable(AppDestinations.HOME.route) {
            HomeScreen()
        }
        composable(AppDestinations.ROUTINES.route) {
            RoutinesScreen()
        }
    }
}