package com.example.itba.hci.ui.components.devices

import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.devices.DoorViewModel

@Composable
fun DoorCard(
    navController: NavController,
    viewModel: DoorViewModel,
    deviceId: String
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.getDevice(deviceId)

    val currentDevice = uiState.currentDevice

    Log.d("DoorCard meta", "Current device: ${currentDevice?.meta}")
    Log.d("DoorCard status", "Current device: ${currentDevice?.status}")
    Log.d("DoorCard lock", "Current device: ${currentDevice?.lock}")

    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
         LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.navigate("devices_screen") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
            item {
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
             item {
                 DoorControl(viewModel, deviceId)
             }
        }
    }
}




@Composable
fun DoorControl(viewModel: DoorViewModel, deviceId: String) {

    val uiState by viewModel.uiState.collectAsState()

    viewModel.getDevice(deviceId)

    val currentDevice = uiState.currentDevice



    var isDoorOpen by remember { mutableStateOf(currentDevice?.status) }
    var isDoorLocked by remember { mutableStateOf(currentDevice?.lock) }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val doorIcon: Painter = painterResource(
            id = when {
                isDoorOpen != "closed" -> R.drawable.mdi_door_open
                isDoorLocked != "unlocked" -> R.drawable.mdi_door_closed_lock
                else -> R.drawable.mdi_door_closed
            }
        )

        Image(
            painter = doorIcon,
            contentDescription = if (isDoorOpen != "closed") "Door Open" else "Door Closed",
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { if (isDoorOpen == "closed" && isDoorLocked == "unlocked") {
                                viewModel.open()
                                    isDoorOpen = "open"
                            } else {
                                viewModel.close()
                                isDoorOpen = "closed"
                            }
        }) {
            Text(if (isDoorOpen != "closed") "Close" else "Open")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (isDoorOpen == "closed" && isDoorLocked == "unlocked") {
                    viewModel.lock()
                    isDoorLocked = "locked"
                    val notificationText = "The door has been locked"
                    sendNotification(context, notificationText)
                } else if(isDoorOpen == "closed" && isDoorLocked != "unlocked") {
                    viewModel.unlock()
                    isDoorLocked = "unlocked"
                    val notificationText = "The door has been unlocked"
                    sendNotification(context, notificationText)
                }
            }
        ) {
            Icon(
                painter = painterResource(
                    id = if (isDoorLocked != "unlocked") R.drawable.mdi_lock_open else R.drawable.mdi_lock
                ),
                contentDescription = if (isDoorLocked != "unlocked") "Unlock" else "Lock"
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
//
//@Preview(showBackground = true)
//@Composable
//fun DoorPreview() {
//    HomeDomeTheme {
//        DoorCard(
//            navController = rememberNavController(),
//            deviceId = "2"
//        )
//    }
//}
