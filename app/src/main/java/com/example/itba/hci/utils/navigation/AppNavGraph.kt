package com.example.itba.hci.utils.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.itba.hci.ui.RoutineViewModel
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

    }
}