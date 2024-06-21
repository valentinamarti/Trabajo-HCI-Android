package com.example.itba.hci.utils.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.itba.hci.model.DeviceType
import com.example.itba.hci.ui.components.devices.BlindsCard
import com.example.itba.hci.ui.components.devices.DoorCard
import com.example.itba.hci.ui.components.devices.FridgeCard
import com.example.itba.hci.ui.components.devices.SpeakerCard
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
        composable("deviceDetail/{deviceType}/{deviceId}") { backStackEntry ->
            val deviceType = backStackEntry.arguments?.getString("deviceType")
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            Log.d("AppNavGraph", "deviceType: $deviceType, deviceId: $deviceId")
            if (deviceType != null && deviceId != null) {
                when (deviceType) {
                    "FRIDGE"  -> {
                        FridgeCard(deviceId = deviceId)
                    }
                    "DOOR" -> {
                        DoorCard(deviceId = deviceId)
                    }
                    "SPEAKER" -> {
                        SpeakerCard(deviceId = deviceId)
                    }
                    "BLIND" -> {
                        BlindsCard(deviceId = deviceId)
                    }
                    else -> {
                        Text("Unknown device type")
                    }
                }
            }
        }

    }
}