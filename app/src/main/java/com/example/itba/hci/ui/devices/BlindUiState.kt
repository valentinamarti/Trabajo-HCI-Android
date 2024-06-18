package com.example.itba.hci.ui.devices

import com.example.itba.hci.model.Error
import com.example.itba.hci.model.Blind

data class BlindUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Blind? = null
)

val BlindUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading