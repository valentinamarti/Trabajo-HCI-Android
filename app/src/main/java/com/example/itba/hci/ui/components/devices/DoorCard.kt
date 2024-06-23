package com.example.itba.hci.ui.components.devices

import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.model.Door
import com.example.itba.hci.ui.devices.DoorViewModel

@Composable
fun DoorCard(
    navController: NavController,
    viewModel: DoorViewModel,
    deviceId: String
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice

    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
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
            if (isPortrait)
                VerticalDoorControl(viewModel = viewModel, currentDevice = currentDevice)
            else
                HorizontalDoorControl(viewModel = viewModel, currentDevice = currentDevice)
        }
    }
}

@Composable
fun VerticalDoorControl(viewModel: DoorViewModel, currentDevice: Door?) {
    var isDoorOpen by remember { mutableStateOf(currentDevice?.status) }
    var isDoorLocked by remember { mutableStateOf(currentDevice?.lock) }
    val context = LocalContext.current

    LaunchedEffect(currentDevice) {
        isDoorOpen = currentDevice?.status
        isDoorLocked = currentDevice?.lock
    }

    val notificationTextLocked = stringResource(id = R.string.notification_locked)
    val notificationTextUnlocked = stringResource(id = R.string.notification_unlocked)
    val notificationTitle = stringResource(id = R.string.notification_title)
    val notificationError = stringResource(id = R.string.notification_error)

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
            contentDescription = if (isDoorOpen != "closed") stringResource(id = R.string.door_opened) else stringResource(id = R.string.door_closed),
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (isDoorOpen == "closed" && isDoorLocked == "unlocked") {
                viewModel.open()
                isDoorOpen = "open"
            } else {
                viewModel.close()
                isDoorOpen = "closed"
            }
        }) {
            Text(if (isDoorOpen != "closed") stringResource(id = R.string.closed) else stringResource(id = R.string.opened))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (isDoorOpen == "closed" && isDoorLocked == "unlocked") {
                    viewModel.lock()
                    isDoorLocked = "locked"
                    sendNotification(context, notificationTextLocked, notificationTitle, notificationError)
                } else if (isDoorOpen == "closed") {
                    viewModel.unlock()
                    isDoorLocked = "unlocked"
                    sendNotification(context, notificationTextUnlocked, notificationTitle, notificationError)
                }
            }
        ) {
            Icon(
                painter = painterResource(
                    id = if (isDoorLocked != "unlocked") R.drawable.mdi_lock_open else R.drawable.mdi_lock
                ),
                contentDescription = if (isDoorLocked != "unlocked") stringResource(id = R.string.unlocked) else  stringResource(id = R.string.locked)
            )
        }
    }
}

@Composable
fun HorizontalDoorControl(viewModel: DoorViewModel, currentDevice: Door?) {
    var isDoorOpen by remember { mutableStateOf(currentDevice?.status) }
    var isDoorLocked by remember { mutableStateOf(currentDevice?.lock) }
    val context = LocalContext.current

    LaunchedEffect(currentDevice) {
        isDoorOpen = currentDevice?.status
        isDoorLocked = currentDevice?.lock
    }

    val notificationTextLocked = stringResource(id = R.string.notification_locked)
    val notificationTextUnlocked = stringResource(id = R.string.notification_unlocked)
    val notificationTitle = stringResource(id = R.string.notification_title)
    val notificationError = stringResource(id = R.string.notification_error)

    Row(
        horizontalArrangement = Arrangement.Center,
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
            contentDescription = if (isDoorOpen != "closed")  stringResource(id = R.string.door_opened) else  stringResource(id = R.string.door_closed),
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Button(onClick = {
                if (isDoorOpen == "closed" && isDoorLocked == "unlocked") {
                    viewModel.open()
                    isDoorOpen = "open"
                } else {
                    viewModel.close()
                    isDoorOpen = "closed"
                }
            }) {
                Text(text = if (isDoorOpen != "closed")  stringResource(id = R.string.closed) else  stringResource(id = R.string.opened),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 2.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (isDoorOpen == "closed" && isDoorLocked == "unlocked") {
                        viewModel.lock()
                        isDoorLocked = "locked"
                        sendNotification(context, notificationTextLocked, notificationTitle, notificationError)
                    } else if (isDoorOpen == "closed") {
                        viewModel.unlock()
                        isDoorLocked = "unlocked"
                        sendNotification(context, notificationTextUnlocked, notificationTitle, notificationError)
                    }
                },
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(
                        id = if (isDoorLocked != "unlocked") R.drawable.mdi_lock_open else R.drawable.mdi_lock
                    ),
                    contentDescription = if (isDoorLocked != "unlocked") stringResource(id = R.string.unlocked) else stringResource(id = R.string.locked)
                )
            }
        }
    }
}

fun sendNotification(context: Context, text: String, title: String, error: String) {
    val builder = NotificationCompat.Builder(context, "door_lock_channel")
        .setSmallIcon(R.drawable.homedome)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    } else {
        println(error)
    }
}
