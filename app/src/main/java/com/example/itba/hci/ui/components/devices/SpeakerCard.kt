package com.example.itba.hci.ui.components.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.theme.HomeDomeTheme

@Composable
fun SpeakerCard(navController: NavController,deviceId: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
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
                IconButton(onClick = { navController.navigate("devices_screen") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Parlante", style = MaterialTheme.typography.bodyLarge)
                    Text("Room 1", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text("Icon", modifier = Modifier.padding(end = 16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            SpeakerControl()

            Spacer(modifier = Modifier.height(16.dp))

            GenreSelection()

            Spacer(modifier = Modifier.height(16.dp))

            Playlist()
        }
    }
}

@Composable
fun SpeakerControl() {
    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /* Handle skip previous action */ }) {
                Icon(painterResource(id = R.drawable.mdi_skip_previous_circle), contentDescription = "Previous")
            }
            IconButton(onClick = { isPlaying = !isPlaying }) {
                Icon(
                    painterResource(id = if (isPlaying) R.drawable.mdi_pause_circle else R.drawable.mdi_play_circle),
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }
            IconButton(onClick = { /* Handle skip next action */ }) {
                Icon(painterResource(id = R.drawable.mdi_skip_next_circle), contentDescription = "Next")
            }
            IconButton(onClick = { /* Handle stop action */ }) {
                Icon(painterResource(id = R.drawable.mdi_square), contentDescription = "Stop")
            }
        }
    }
}

@Composable
fun GenreSelection() {
    var expanded by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf("Select Genre") }

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
                Text(selectedGenre)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                DropdownMenuItem(
                    onClick = {
                        selectedGenre = "Rock"
                        expanded = false
                    },
                    text = { Text("Rock") }
                )
                DropdownMenuItem(
                    onClick = {
                        selectedGenre = "Pop"
                        expanded = false
                    },
                    text = { Text("Pop") }
                )
                DropdownMenuItem(
                    onClick = {
                        selectedGenre = "Jazz"
                        expanded = false
                    },
                    text = { Text("Jazz") }
                )
            }
        }
    }
}

@Composable
fun Playlist() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text("Playlist", style = MaterialTheme.typography.bodyLarge)

        val songs = listOf(
            "Memories",
            "The Kids Are Coming",
            "Don't Call Me Angel",
            "Graveyard",
            "Liar"
        )

        songs.forEach { song ->
            Button(
                onClick = { /* Handle song click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(song)
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun SpeakerPreview() {
//    HomeDomeTheme {
//        SpeakerCard("4")
//    }
//}
