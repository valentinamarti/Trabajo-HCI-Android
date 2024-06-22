package com.example.itba.hci.ui.components.devices

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.devices.FridgeViewModel

@Composable
fun FridgeCard(navController: NavController, viewModel: FridgeViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice

    Log.d("FridgeCard", "Current device: $currentDevice")
    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
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
                        painter = painterResource(R.drawable.fridge_outline),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "${currentDevice?.name}",
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
            TemperatureControl(viewModel, deviceId)
            FreezerTemperatureControl(viewModel, deviceId)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = stringResource(id = R.string.fridge_mode),
                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 4.dp),
                    fontSize = 14.sp)
            }
            ModeSelection(viewModel, deviceId)
        }
    }
}

@Composable
fun TemperatureControl(viewModel: FridgeViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice
    var temperature by remember { mutableStateOf(currentDevice?.temperature) }

    LaunchedEffect(currentDevice?.temperature) {
        if (currentDevice?.temperature != null) {
            temperature = currentDevice.temperature
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Text(text = stringResource(id = R.string.fridge_temperature),
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                fontSize = 14.sp)
            Box(
                modifier = Modifier
                    .size(30.dp) // Ajusta el tamaño del área clickeable
                    .clickable {
                        if (temperature != null && temperature!! > 0) {
                            temperature = temperature!! - 1
                            viewModel.setTemperature(temperature ?: 0)
                        }
                    }
                    .background(color = MaterialTheme.colorScheme.background,shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "-",
                    fontSize = 10.sp, // Ajusta el tamaño del texto
                    color = Color.Black // Color del texto
                )
            }

            Text("${temperature ?: 0}°C", modifier = Modifier.padding(horizontal = 8.dp))
            Box(
                modifier = Modifier
                    .size(30.dp) // Ajusta el tamaño del área clickeable
                    .clickable {
                        if (temperature != null && temperature!! < 12) {
                            temperature = temperature!! + 1
                            viewModel.setTemperature(temperature ?: 0)
                        }
                    }
                    .background(color = MaterialTheme.colorScheme.background,shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+",
                    fontSize = 10.sp, // Ajusta el tamaño del texto
                    color = Color.Black // Color del texto
                )
            }
        }
    }
}

@Composable
fun FreezerTemperatureControl(viewModel: FridgeViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice
    Log.d("current device temperature", "${currentDevice}")
    Log.d("freezer temperature", "${currentDevice?.freezerTemperature}")

    var freezerTemperature by remember { mutableStateOf(currentDevice?.freezerTemperature) }

    LaunchedEffect(currentDevice?.freezerTemperature) {
        if (currentDevice?.freezerTemperature != null) {
            freezerTemperature = currentDevice.freezerTemperature
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Text(text = stringResource(id = R.string.freezer_temperature),
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                fontSize = 14.sp)

            Box(
                modifier = Modifier
                    .size(30.dp) // Ajusta el tamaño del área clickeable
                    .clickable {
                        if (freezerTemperature != null && freezerTemperature!! > -20) {
                            freezerTemperature = freezerTemperature!! - 1
                            viewModel.setFreezerTemperature(freezerTemperature ?: 0)
                        }
                    }
                    .background(color = MaterialTheme.colorScheme.background,shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "-",
                    fontSize = 10.sp, // Ajusta el tamaño del texto
                    color = Color.Black // Color del texto
                )
            }

            Text("${freezerTemperature ?: 0}°C", modifier = Modifier.padding(horizontal = 8.dp))

            Box(
                modifier = Modifier
                    .size(30.dp) // Ajusta el tamaño del área clickeable
                    .clickable {
                        if (freezerTemperature != null && freezerTemperature!! < 0) {
                            freezerTemperature = freezerTemperature!! + 1
                            viewModel.setFreezerTemperature(freezerTemperature ?: 0)
                        }
                    }
                    .background(color = MaterialTheme.colorScheme.background,shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+",
                    fontSize = 10.sp, // Ajusta el tamaño del texto
                    color = Color.Black // Color del texto
                )
            }

        }
    }
}

@Composable
fun ModeSelection(viewModel: FridgeViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    val currentDevice = uiState.currentDevice

    var expanded by remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf(currentDevice?.mode ?: "Select Genre") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedMode)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(220.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                val modes = listOf("Default", "Vacation", "Party")
                modes.forEach { mode ->
                    DropdownMenuItem(
                        onClick = {
                            selectedMode = mode
                            expanded = false
                            viewModel.setMode(mode.lowercase())
                            viewModel.getDevice(deviceId)
                        },
                        text = { Text(mode) }
                    )
                }
            }
        }
    }
}
