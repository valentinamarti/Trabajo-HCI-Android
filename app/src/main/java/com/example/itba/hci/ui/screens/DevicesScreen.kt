package com.example.itba.hci.ui.screens

import android.util.Log
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
import androidx.navigation.NavHostController
import com.example.itba.hci.R
import com.example.itba.hci.ui.components.cards.DeviceCard
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.devices.DevicesViewModel
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.devices.FridgeViewModel
import com.example.itba.hci.ui.devices.SpeakerViewModel
import com.example.itba.hci.ui.theme.screenTitle

@Composable
fun DevicesScreen(
    navController: NavHostController,
    devicesViewModel: DevicesViewModel,
    doorViewModel: DoorViewModel,
    fridgeViewModel: FridgeViewModel,
    speakerViewModel: SpeakerViewModel,
    blindViewModel: BlindViewModel
){
    val uiState by devicesViewModel.uiState.collectAsState()
    Log.d("DevicesScreen", "Devices list is empty: ${uiState.devices.isEmpty()}")
    Column(modifier = Modifier
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
                    onClick = { navController.navigate("deviceDetail/${device.type}/${device.id}")},
                    doorViewModel = doorViewModel,
                    fridgeViewModel = fridgeViewModel,
                    speakerViewModel = speakerViewModel,
                    blindViewModel = blindViewModel
                    )
            }
        }
    }
}
