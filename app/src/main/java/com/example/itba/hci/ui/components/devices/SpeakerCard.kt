package com.example.itba.hci.ui.components.devices

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.ui.devices.SpeakerViewModel

@Composable
fun SpeakerCard(navController: NavController, viewModel: SpeakerViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice

    Log.d("SpeakerCard", "Current device: $currentDevice")

    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.speaker),
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

                SpeakerControl(viewModel, deviceId)

                Spacer(modifier = Modifier.height(16.dp))

                GenreSelection(viewModel, deviceId)

                Spacer(modifier = Modifier.height(16.dp))

                Playlist()
            }
        }
    }
}

@Composable
fun SpeakerControl(viewModel: SpeakerViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice

    var isPlaying by remember { mutableStateOf(currentDevice?.status) }
    var volume by remember { mutableStateOf(currentDevice?.volume?.toFloat() ?: 0f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Display the song title and artist
        Log.d("current song:", "${currentDevice?.song}")
        if (currentDevice?.song != null) {
            Text(
                text = "${stringResource(id = R.string.song_title)}: ${currentDevice.song.title}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "${stringResource(id = R.string.song_artist)}: ${currentDevice.song.artist}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                IconButton(onClick = {
                    viewModel.previousSong()
                    viewModel.getDevice(deviceId) // refresh device data
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.mdi_skip_previous_circle),
                        contentDescription = stringResource(id = R.string.previous)
                    )
                }
                IconButton(onClick = {
                    if (isPlaying == "playing") {
                        viewModel.pause()
                        isPlaying = "paused"
                    } else if (isPlaying == "paused") {
                        viewModel.resume()
                        isPlaying = "playing"
                    } else if (isPlaying == "stopped") {
                        viewModel.play()
                        isPlaying = "playing"
                    }
                    viewModel.getDevice(deviceId)
                }) {
                    Icon(
                        painter = painterResource(id = if (isPlaying == "playing") R.drawable.mdi_pause_circle else R.drawable.mdi_play_circle),
                        contentDescription = if (isPlaying == "playing") stringResource(id = R.string.pause) else stringResource(id = R.string.play)
                    )
                }
                IconButton(onClick = {
                    viewModel.nextSong()
                    viewModel.getDevice(deviceId)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.mdi_skip_next_circle),
                        contentDescription = stringResource(id = R.string.next)
                    )
                }
                IconButton(onClick = {
                    viewModel.stop()
                    isPlaying = "stopped"
                    viewModel.getDevice(deviceId)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.mdi_square),
                        contentDescription = stringResource(id = R.string.stop)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp) // Adjust the spacing as needed
        ) {
            Text("${stringResource(id = R.string.volume)}: ${volume.toInt()}")

            Slider(
                value = volume,
                onValueChange = {
                    Log.d("volume is:", "${volume.toInt()}")
                    volume = it
                    viewModel.setVolume(it.toInt())
                },
                valueRange = 0f..10f,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun GenreSelection(viewModel: SpeakerViewModel, deviceId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(deviceId) {
        viewModel.getDevice(deviceId)
    }

    val currentDevice = uiState.currentDevice

    var expanded by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf(currentDevice?.genre ?: "Select Genre") }

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
                val genres = listOf("Classical", "Country", "Dance", "Latina", "Pop", "Rock")
                genres.forEach { genre ->
                    DropdownMenuItem(
                        onClick = {
                            selectedGenre = genre
                            expanded = false
                            viewModel.setGenre(genre.toLowerCase())
                            viewModel.getDevice(deviceId)
                        },
                        text = { Text(genre) }
                    )
                }
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
        Text(stringResource(id = R.string.playlist), style = MaterialTheme.typography.bodyLarge)

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
