package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDevice

abstract class Device(
    val id: String?,
    val name: String,
    val type: DeviceType
) {
    abstract fun asRemoteModel(): RemoteDevice<*>
}