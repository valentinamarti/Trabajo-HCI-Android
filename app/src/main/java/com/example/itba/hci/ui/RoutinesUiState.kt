package com.example.itba.hci.ui

import com.example.itba.hci.model.Error
import com.example.itba.hci.model.Routine


data class RoutinesUiState(
    val isFetching: Boolean = false,
    val error: Error? = null,
    val routines: List<Routine> = emptyList()
)