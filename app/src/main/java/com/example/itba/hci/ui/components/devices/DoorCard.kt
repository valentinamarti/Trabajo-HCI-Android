package com.example.itba.hci.ui.components.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itba.hci.R
import com.example.itba.hci.ui.theme.HomeDomeTheme

@Composable
fun DoorCard(deviceId: String) {
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
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Puerta", style = MaterialTheme.typography.bodyLarge)
                    Text("Room 1", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("Icon", modifier = Modifier.padding(end = 16.dp))
            }

            DoorControl()
        }
    }
}

@Composable
fun DoorControl() {
    var isDoorOpen by remember { mutableStateOf(false) }
    var isDoorLocked by remember { mutableStateOf(false) }

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

@Preview(showBackground = true)
@Composable
fun DoorPreview() {
    HomeDomeTheme {
        DoorCard("2")
    }
}
