package com.example.itba.hci.ui.devices

import com.example.itba.hci.model.Error
import com.example.itba.hci.model.Speaker

data class SpeakerUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Speaker? = null
)

val SpeakerUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading
