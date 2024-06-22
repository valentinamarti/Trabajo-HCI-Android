package com.example.itba.hci.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.itba.hci.R
import com.example.itba.hci.model.Device
import com.example.itba.hci.model.DeviceType
import com.example.itba.hci.model.Routine
import com.example.itba.hci.ui.RoutineViewModel
import com.example.itba.hci.ui.components.RoutineView
import com.example.itba.hci.ui.components.cards.DeviceCard
import com.example.itba.hci.ui.components.cards.RoutineCard
import com.example.itba.hci.ui.components.devices.BlindsCard
import com.example.itba.hci.ui.components.devices.DoorCard
import com.example.itba.hci.ui.components.devices.FridgeCard
import com.example.itba.hci.ui.components.devices.SpeakerCard
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.devices.DevicesViewModel
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.devices.FridgeViewModel
import com.example.itba.hci.ui.devices.SpeakerViewModel
import com.example.itba.hci.ui.theme.noElements
import com.example.itba.hci.ui.theme.screenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController,
               devicesViewModel: DevicesViewModel,
               doorViewModel: DoorViewModel,
               fridgeViewModel: FridgeViewModel,
               speakerViewModel: SpeakerViewModel,
               blindViewModel: BlindViewModel,
               routineViewModel: RoutineViewModel
) {

    val devicesUiState by devicesViewModel.uiState.collectAsState()
    val routineUiState by routineViewModel.uiState.collectAsState()

    var showDeviceDialog by remember { mutableStateOf(false) }
    var selectedDevice by remember { mutableStateOf<Device?>(null) }

    var showRoutineDialog by remember { mutableStateOf(false) }
    var selectedRoutine by remember { mutableStateOf<Routine?>(null) }

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
            if (devicesUiState.devices.filter { it.meta?.favorite == true }.isEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.no_favorite_devices),
                        style = noElements,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                items(devicesUiState.devices.filter { it.meta?.favorite == true }) { device ->
                    DeviceCard(
                        device = device,
                        onClick = {selectedDevice = device; showDeviceDialog = true},
                        doorViewModel = doorViewModel,
                        fridgeViewModel = fridgeViewModel,
                        speakerViewModel = speakerViewModel,
                        blindViewModel = blindViewModel
                    )
                }
            }
            item {
                Text(text = stringResource(id = R.string.fav_routines),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
            if (routineUiState.routines.filter { it.meta?.favorite!!}.isEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.no_favorite_routines),
                        style = noElements,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                items(routineUiState.routines.filter { it.meta?.favorite!! }) { routine ->
                    routine.meta?.color?.primary?.let {
                        routine.meta.color.secondary?.let { it1 ->
                            RoutineCard(
                                routine = routine,
                                viewModel = routineViewModel,
                                onClick = { selectedRoutine = routine; showRoutineDialog = true }
                            )
                        }
                    }
                }
            }
        }
        if (showDeviceDialog && selectedDevice != null) {
            BasicAlertDialog(onDismissRequest = { showDeviceDialog = false },
                content = {
                    when (selectedDevice?.type) {
                        DeviceType.DOOR -> selectedDevice!!.id?.let {
                            DoorCard(navController, doorViewModel,
                                it
                            )
                        }

                        DeviceType.FRIDGE -> selectedDevice!!.id?.let {
                            FridgeCard(navController, fridgeViewModel,
                                it
                            )
                        }

                        DeviceType.SPEAKER -> selectedDevice!!.id?.let {
                            SpeakerCard(navController, speakerViewModel,
                                it
                            )
                        }

                        DeviceType.BLIND -> selectedDevice!!.id?.let {
                            BlindsCard(navController, blindViewModel,
                                it
                            )
                        }

                        else -> Text("Tipo de dispositivo desconocido")
                    }
                }
            )
        }
        if (showRoutineDialog && selectedRoutine != null) {
            BasicAlertDialog(
                onDismissRequest = { showRoutineDialog = false },
                content = { selectedRoutine!!.id?.let { RoutineView(navController,routineViewModel, it) } }
            )
        }
    }
}

