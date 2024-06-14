package com.example.itba.hci.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.itba.hci.screens.DevicesScreen
import com.example.itba.hci.screens.HomeScreen
import com.example.itba.hci.screens.RoutinesScreen


@Composable
fun NavigationBarGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationScreen.Home.route) {
        composable(NavigationScreen.Routines.route) { RoutinesScreen(navController) }
        composable(NavigationScreen.Home.route) { HomeScreen(navController) }
        composable(NavigationScreen.Devices.route) { DevicesScreen(navController) }
    }
}