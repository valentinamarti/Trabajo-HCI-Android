package com.example.itba.hci.ui

import com.example.itba.hci.model.Error
import com.example.itba.hci.model.Routine


data class RoutinesUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val routines: List<Routine> = emptyList(),
    val currentRoutine: Routine? = null
)

val RoutinesUiState.canGetCurrent: Boolean get() = currentRoutine != null
val RoutinesUiState.canModify: Boolean get() = currentRoutine != null
val RoutinesUiState.canDelete: Boolean get() = canModify
