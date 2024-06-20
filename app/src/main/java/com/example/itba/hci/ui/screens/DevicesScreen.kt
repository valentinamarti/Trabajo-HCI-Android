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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.itba.hci.R
import com.example.itba.hci.ui.components.cards.DeviceCard
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.devices.DevicesViewModel
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.devices.FridgeViewModel
import com.example.itba.hci.ui.devices.SpeakerViewModel
import com.example.itba.hci.ui.getViewModelFactory
import com.example.itba.hci.ui.theme.screenTitle

/*
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
import com.example.itba.hci.R
import com.example.itba.hci.ui.components.cards.DeviceCard
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.devices.DevicesViewModel
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.devices.FridgeViewModel
import com.example.itba.hci.ui.devices.SpeakerViewModel
import com.example.itba.hci.ui.getViewModelFactory
import com.example.itba.hci.ui.theme.screenTitle
*/


@Composable
fun DevicesScreen(
    viewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
    doorViewModel: DoorViewModel = viewModel(factory = getViewModelFactory()),
    fridgeViewModel: FridgeViewModel = viewModel(factory = getViewModelFactory()),
    blindViewModel: BlindViewModel = viewModel(factory = getViewModelFactory()),
    speakerViewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory())
){
//    Log.d("DeviceScreen", "DeviceScreen started")
    val uiState by viewModel.uiState.collectAsState()
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
                Log.d("DevicesScreen", "Devices primary color: ${device.meta?.color?.primary}")
                DeviceCard(
                    text = device.name,
                    deviceType = device.type,
                    primaryColor = device.meta?.color?.primary ?: "#FFFFFF",
                    secondaryColor = device.meta?.color?.secondary ?: "#FFFFFF",
                    )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DevicesScreenPreview() {
//    DevicesScreen(navController = rememberNavController() ,paddingValues = PaddingValues(0.dp))
//}