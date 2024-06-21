package com.example.itba.hci.utils.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.itba.hci.ui.RoutineViewModel
import com.example.itba.hci.ui.components.RoutineView
import com.example.itba.hci.ui.components.devices.BlindsCard
import com.example.itba.hci.ui.components.devices.DoorCard
import com.example.itba.hci.ui.components.devices.FridgeCard
import com.example.itba.hci.ui.components.devices.SpeakerCard
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.devices.DevicesViewModel
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.devices.FridgeViewModel
import com.example.itba.hci.ui.devices.SpeakerViewModel
import com.example.itba.hci.ui.getViewModelFactory
import com.example.itba.hci.ui.screens.DevicesScreen
import com.example.itba.hci.ui.screens.HomeScreen
import com.example.itba.hci.ui.screens.RoutinesScreen

@Composable
fun AppNavGraph(navController: NavHostController,
                innerPadding: PaddingValues,
                routineViewModel: RoutineViewModel = viewModel(factory = getViewModelFactory()),
                devicesViewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
                doorViewModel: DoorViewModel = viewModel(factory = getViewModelFactory()),
                fridgeViewModel: FridgeViewModel = viewModel(factory = getViewModelFactory()),
                blindViewModel: BlindViewModel = viewModel(factory = getViewModelFactory()),
                speakerViewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory())
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(AppDestinations.DEVICES.route) {
            DevicesScreen(navController = navController,
                devicesViewModel = devicesViewModel,
                doorViewModel = doorViewModel,
                fridgeViewModel = fridgeViewModel,
                speakerViewModel = speakerViewModel,
                blindViewModel = blindViewModel)
        }
        composable(AppDestinations.HOME.route) {
            HomeScreen(navController = navController,
                devicesViewModel = devicesViewModel,
                routineViewModel = routineViewModel,
                doorViewModel = doorViewModel,
                fridgeViewModel = fridgeViewModel,
                speakerViewModel = speakerViewModel,
                blindViewModel = blindViewModel)
        }
        composable(AppDestinations.ROUTINES.route) {
            RoutinesScreen(navController = navController, viewModel = routineViewModel)
        }
        composable("routineDetail/{routineId}") { backStackEntry ->
            val routineId = backStackEntry.arguments?.getString("routineId")
            Log.d("AppNavGraph", "RoutineId: $routineId")
            if (routineId != null) {
                RoutineView(navController = navController, viewModel = routineViewModel, routineId = routineId)
            }
        }

        composable("deviceDetail/{deviceType}/{deviceId}") { backStackEntry ->
            val deviceType = backStackEntry.arguments?.getString("deviceType")
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            Log.d("AppNavGraph", "deviceType: $deviceType, deviceId: $deviceId")
            if (deviceType != null && deviceId != null) {
                when (deviceType) {
                    "FRIDGE"  -> {
                        FridgeCard(navController = navController,deviceId = deviceId, viewModel = fridgeViewModel)
                    }
                    "DOOR" -> {
                        DoorCard(navController = navController,deviceId = deviceId, viewModel = doorViewModel)
                    }
                    "SPEAKER" -> {
                        SpeakerCard(navController = navController,deviceId = deviceId, viewModel = speakerViewModel)
                    }
                    "BLIND" -> {
                        BlindsCard(navController = navController,deviceId = deviceId, viewModel = blindViewModel)
                    }
                    else -> {
                        Text("Unknown device type")
                    }
                }
            }
        }

    }
}