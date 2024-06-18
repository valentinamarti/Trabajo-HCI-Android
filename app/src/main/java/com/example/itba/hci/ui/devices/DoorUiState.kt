package com.example.itba.hci.ui.devices

import com.example.itba.hci.model.Door
import com.example.itba.hci.model.Error

data class DoorUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Door? = null
)

val DoorUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading