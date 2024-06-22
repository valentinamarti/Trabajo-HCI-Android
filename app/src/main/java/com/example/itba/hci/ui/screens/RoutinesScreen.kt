package com.example.itba.hci.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.itba.hci.R
import com.example.itba.hci.model.Routine
import com.example.itba.hci.ui.RoutineViewModel
import com.example.itba.hci.ui.components.RoutineView
import com.example.itba.hci.ui.components.cards.RoutineCard
import com.example.itba.hci.ui.theme.screenTitle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen(
    navController: NavHostController,
    viewModel: RoutineViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    Log.d("RoutinesScreen", "Routines list is empty: ${uiState.routines.isEmpty()}")

    var showDialog by remember { mutableStateOf(false) }
    var selectedRoutine by remember { mutableStateOf<Routine?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.bottom_navigation_routines),
            style = screenTitle,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.routines) { routine ->
                routine.meta?.color?.primary?.let {
                    routine.meta.color.secondary?.let { it1 ->
                        RoutineCard(
                            routine = routine,
                            viewModel = viewModel,
                            onClick = { selectedRoutine = routine; showDialog = true },
                        )
                    }
                }
            }
        }
    }
    if (showDialog && selectedRoutine != null) {
        BasicAlertDialog(
            onDismissRequest = { showDialog = false },
            content = {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .padding(vertical = 100.dp)
                        .wrapContentHeight()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            IconButton(onClick = { selectedRoutine = null; showDialog = false }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                        selectedRoutine!!.id?.let { RoutineView(navController, viewModel, it) }
                    }
                }
            }
        )
    }
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
