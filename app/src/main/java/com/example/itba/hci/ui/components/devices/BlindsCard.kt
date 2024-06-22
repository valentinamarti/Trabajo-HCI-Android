package com.example.itba.hci.ui.components.devices

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.devices.BlindViewModel

@Composable
fun BlindsCard(navController: NavController, viewModel: BlindViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice

    Log.d("BlindsCard", "Current device: $currentDevice")

    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.blinds),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = currentDevice?.name ?: "",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        currentDevice?.room?.let { room ->
                            Text(room.name, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            BlindControl(viewModel, deviceId)
        }
    }
}

@Composable
fun BlindControl(viewModel: BlindViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice
    var sliderValue by remember { mutableStateOf(currentDevice?.level?.toFloat() ?: 0f) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                    viewModel.setLevel(it.toInt())
                },
                valueRange = 0f..100f,
                modifier = Modifier
                    .height(200.dp)
                    .verticalScroll(rememberScrollState())
                    .rotate(270f) // Rotate the slider to make it vertical
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                sliderValue = 0f
                viewModel.setLevel(0)
            }) {
                Text(stringResource(id = R.string.open))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${stringResource(id = R.string.status)}: ${sliderValue.toInt()}%",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                sliderValue = 100f
                viewModel.setLevel(100)
            }) {
                Text(stringResource(id = R.string.close))
            }
        }
    }
}
