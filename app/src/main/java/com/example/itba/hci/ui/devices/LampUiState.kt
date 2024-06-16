package com.example.itba.hci.ui.devices

import com.example.itba.hci.model.Error
import com.example.itba.hci.model.Lamp

data class LampUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Lamp? = null
)

val LampUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading