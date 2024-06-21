package com.example.itba.hci.ui.components.devices

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.devices.BlindViewModel
import com.example.itba.hci.ui.getViewModelFactory

@Composable
fun BlindsCard(navController: NavController, viewModel: BlindViewModel = viewModel(factory = getViewModelFactory()), deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()


    viewModel.getDevice(deviceId)

    val currentDevice = uiState.currentDevice

    Log.d("DoorCard", "Current device: $currentDevice")

    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("devices_screen") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.blinds),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("${currentDevice?.name}", style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ))
                    currentDevice?.room?.let { room ->
                        Text("$room", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BlindControl()
        }
    }
}

@Composable
fun BlindControl() {
    var sliderValue by remember { mutableStateOf(100f) }

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
                onValueChange = { sliderValue = it },
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
            Button(onClick = { sliderValue = 0f }) {
                Text("Abrir")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Estado: ${sliderValue.toInt()}%",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { sliderValue = 100f }) {
                Text("Cerrar")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BlindsPreview() {
//    HomeDomeTheme {
//        BlindsCard("1")
//    }
//}
