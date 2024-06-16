package com.example.itba.hci.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.itba.hci.ui.screens.DevicesScreen
import com.example.itba.hci.ui.screens.HomeScreen
import com.example.itba.hci.ui.screens.RoutinesScreen


@Composable
fun NavigationBarGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = NavigationScreen.Home.route) {
        composable(NavigationScreen.Routines.route) { RoutinesScreen(navController, paddingValues) }
        composable(NavigationScreen.Home.route) { HomeScreen(navController, paddingValues) }
        composable(NavigationScreen.Devices.route) { DevicesScreen(navController, paddingValues) }
    }
}
