package com.example.itba.hci.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.itba.hci.ui.screens.DevicesScreen
import com.example.itba.hci.ui.screens.HomeScreen
import com.example.itba.hci.ui.screens.RoutinesScreen
import com.example.itba.hci.ui.components.devices.BlindsCard
import com.example.itba.hci.ui.components.devices.DoorCard
import com.example.itba.hci.ui.components.devices.FridgeCard
import com.example.itba.hci.ui.components.devices.SpeakerCard

@Composable
fun NavigationBarGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = NavigationScreen.Home.route) {
        composable(NavigationScreen.Routines.route) { RoutinesScreen(navController, paddingValues) }
        composable(NavigationScreen.Home.route) { HomeScreen(navController, paddingValues) }
        composable(NavigationScreen.Devices.route) { DevicesScreen(navController, paddingValues) }
        composable(
            "deviceDetail/{deviceType}/{deviceId}",
            arguments = listOf(
                navArgument("deviceType") { type = NavType.StringType },
                navArgument("deviceId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val deviceType = backStackEntry.arguments?.getString("deviceType") ?: ""
            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
            when (deviceType) {
                "Parlante" -> SpeakerCard(deviceId, paddingValues)
                "Heladera" -> FridgeCard(deviceId, paddingValues)
                "Persiana" -> BlindsCard(deviceId, paddingValues)
                "Puerta" -> DoorCard(deviceId, paddingValues)
                else -> Text("Unknown Device")
            }
        }
    }
}
