package com.example.itba.hci.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.remote.model.RemoteAction
import com.example.itba.hci.ui.RoutineViewModel
import com.example.itba.hci.ui.screens.toColor


@Composable
fun RoutineView(navController: NavController, viewModel: RoutineViewModel, routineId: String) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.getRoutine(routineId)

    val routine = uiState.currentRoutine

    Log.d("Routine", "Current routine: $routine")


    LaunchedEffect(Unit) {
        viewModel.getRoutine(routineId)
    }

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
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("routine_screen") }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
            Row {

                if (routine != null) {
                    Text(
                        text = routine.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(30.dp)
                    )
                }


            }
            CustomDivider()
            Row {
                Text(
                    text = routine?.meta?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(26.dp)
                )
            }
            CustomDivider()

            if (routine != null) {
                EventContainer(routine = routine)
            }

            CustomDivider()

            if (routine != null) {
                ColorSelector(routine)
            }
        }
    }
}


@Composable
fun CustomDivider() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp),
        color = Color(0xFF8D8888)
    )
}


@Composable
fun EventContainer(routine: com.example.itba.hci.model.Routine) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 20.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.routines_actions),
                        style = MaterialTheme.typography.bodyLarge
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            routine.actions.forEachIndexed { _, action ->
                EventItem(action = action)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun EventItem(action: RemoteAction) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFD5D5D5), shape = RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp, horizontal = 18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = action.actionName ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = action.params.joinToString(", ") { it.toString() },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.weight(1f))


        }

    }
}


@Composable
fun ColorSelector(routine: com.example.itba.hci.model.Routine) {
    val selectedColor = routine.meta?.color

    val colorOptions = listOf(
        "#e8c3ff",
        "#c3e8ff",
        "#ffe7d5"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 18.dp)
    ) {
        Text(
            text = stringResource(id = R.string.color_title),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            colorOptions.forEach { option ->
                val isSelected = selectedColor?.primary == option
                if (selectedColor != null) {
                    Log.d("Colors", "SelectedColor: ${selectedColor.primary}")
                }
                Log.d("Colors", "option: $option")
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(color = option.toColor())
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) Color.Black else Color.Transparent,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

