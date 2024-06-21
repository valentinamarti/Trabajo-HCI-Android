package com.example.itba.hci.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.itba.hci.R
import com.example.itba.hci.ui.RoutineViewModel
import com.example.itba.hci.ui.components.cards.DeviceCard
import com.example.itba.hci.ui.components.cards.RoutineCard
import com.example.itba.hci.ui.devices.DevicesViewModel
import com.example.itba.hci.ui.getViewModelFactory
import com.example.itba.hci.ui.theme.screenTitle

@Composable
fun HomeScreen(navController: NavHostController,
               devicesViewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
               routinesViewModel: RoutineViewModel = viewModel(factory = getViewModelFactory())
) {

    val DevicesUiState by devicesViewModel.uiState.collectAsState()
    val RoutineUiState by routinesViewModel.uiState.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bottom_navigation_home),
            style = screenTitle,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.fav_devices),
                    modifier = Modifier
                            .padding(horizontal = 10.dp)
                )
            }
            items(DevicesUiState.devices.filter { it.meta?.favorite == true }) { device ->
                DeviceCard(
                    device = device,
                    onClick = { navController.navigate("deviceDetail/${device.type}/${device.id}") }
                )
            }

            item {
                Text(text = stringResource(id = R.string.fav_routines),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            items(RoutineUiState.routines.filter { it.favorite }) { routine ->
                routine.color.primary?.let {
                    routine.color.secondary?.let { it1 ->
                        RoutineCard(
                            routine = routine,
                            onClick = { navController.navigate("routineDetail/${routine.id}") }
                        )
                    }
                }
            }
        }
    }
}

