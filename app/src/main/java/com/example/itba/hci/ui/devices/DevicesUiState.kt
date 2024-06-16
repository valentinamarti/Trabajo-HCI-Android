package com.example.itba.hci.ui.devices

import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Error

data class DevicesUiState(
    val isFetching: Boolean = false,
    val error: Error? = null,
    val devices: List<Device> = emptyList()
)