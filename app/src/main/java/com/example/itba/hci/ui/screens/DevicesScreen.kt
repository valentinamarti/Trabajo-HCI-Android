package com.example.itba.hci.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import com.example.itba.hci.ui.components.cards.DeviceCard
import com.example.itba.hci.ui.components.devices.BlindsCard
import com.example.itba.hci.ui.components.devices.DoorCard
import com.example.itba.hci.ui.components.devices.FridgeCard
import com.example.itba.hci.ui.components.devices.SpeakerCard
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.devices.DevicesViewModel
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.devices.FridgeViewModel
import com.example.itba.hci.ui.devices.SpeakerViewModel
import com.example.itba.hci.ui.theme.screenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesScreen(
    navController: NavHostController,
    devicesViewModel: DevicesViewModel,
    doorViewModel: DoorViewModel,
    fridgeViewModel: FridgeViewModel,
    speakerViewModel: SpeakerViewModel,
    blindViewModel: BlindViewModel
) {
    val uiState by devicesViewModel.uiState.collectAsState()
    Log.d("DevicesScreen", "Devices list is empty: ${uiState.devices.isEmpty()}")

    var showDialog by remember { mutableStateOf(false) }
    var selectedDevice by remember { mutableStateOf<Device?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bottom_navigation_devices),
            style = screenTitle,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.devices) { device ->
                DeviceCard(
                    device = device,
                    onClick = {
                        selectedDevice = device
                        showDialog = true
                    },
                    doorViewModel = doorViewModel,
                    fridgeViewModel = fridgeViewModel,
                    speakerViewModel = speakerViewModel,
                    blindViewModel = blindViewModel
                )
            }
        }
    }
    if (showDialog && selectedDevice != null) {
        BasicAlertDialog(onDismissRequest = { showDialog = false },
            content = {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .padding(vertical = 100.dp)
                        .widthIn(min = 300.dp, max = 500.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            IconButton(onClick = { selectedDevice = null; showDialog = false}) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                        when (selectedDevice?.type) {
                            DeviceType.DOOR -> selectedDevice!!.id?.let {
                                DoorCard(
                                    navController, doorViewModel,
                                    it
                                )
                            }

                            DeviceType.FRIDGE -> selectedDevice!!.id?.let {
                                FridgeCard(
                                    navController, fridgeViewModel,
                                    it
                                )
                            }

                            DeviceType.SPEAKER -> selectedDevice!!.id?.let {
                                SpeakerCard(
                                    navController, speakerViewModel,
                                    it
                                )
                            }

                            DeviceType.BLIND -> selectedDevice!!.id?.let {
                                BlindsCard(
                                    navController, blindViewModel,
                                    it
                                )
                            }

                            else -> Text("Tipo de dispositivo desconocido")
                        }
                    }

                }
            }
        )
    }
}

