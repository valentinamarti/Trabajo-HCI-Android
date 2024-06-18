package com.example.itba.hci.ui.devices

import com.example.itba.hci.model.Error
import com.example.itba.hci.model.Fridge

data class FridgeUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Fridge? = null
)

val FridgeUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
