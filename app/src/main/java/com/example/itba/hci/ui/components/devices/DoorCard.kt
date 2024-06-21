package com.example.itba.hci.ui.components.devices

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.devices.DoorViewModel
import com.example.itba.hci.ui.getViewModelFactory
import com.example.itba.hci.ui.theme.HomeDomeTheme

@Composable
fun DoorCard(
    navController: NavController,
    viewModel: DoorViewModel = viewModel(factory = getViewModelFactory()),
    deviceId: String
) {
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
                    painter = painterResource(R.drawable.door),
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

            DoorControl()
        }
    }
}




@Composable
fun DoorControl() {
    var isDoorOpen by remember { mutableStateOf(false) }
    var isDoorLocked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val doorIcon: Painter = painterResource(
            id = when {
                isDoorOpen -> R.drawable.mdi_door_open
                isDoorLocked -> R.drawable.mdi_door_closed_lock
                else -> R.drawable.mdi_door_closed
            }
        )

        Image(
            painter = doorIcon,
            contentDescription = if (isDoorOpen) "Door Open" else "Door Closed",
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { isDoorOpen = !isDoorOpen }) {
            Text(if (isDoorOpen) "Close" else "Open")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (!isDoorOpen) {
                    isDoorLocked = !isDoorLocked
                    val notificationText = if (isDoorLocked) "The door has been locked" else "The door has been unlocked"
                    sendNotification(context, notificationText)
                }
            }
        ) {
            Icon(
                painter = painterResource(
                    id = if (isDoorLocked) R.drawable.mdi_lock_open else R.drawable.mdi_lock
                ),
                contentDescription = if (isDoorLocked) "Unlock" else "Lock"
            )
        }
    }
}

fun sendNotification(context: Context, text: String) {
    val builder = NotificationCompat.Builder(context, "door_lock_channel")
        .setSmallIcon(R.drawable.homedome)
        .setContentTitle("Door State Changed")
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    } else {
        println("Notification permission not granted.")
    }
}

@Preview(showBackground = true)
@Composable
fun DoorPreview() {
    HomeDomeTheme {
        DoorCard(
            navController = rememberNavController(),
            deviceId = "2"
        )
    }
}
