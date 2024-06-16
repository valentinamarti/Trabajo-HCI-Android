package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDevice
import com.example.itba.hci.remote.model.RemoteDeviceMeta


abstract class Device(
    val id: String?,
    val name: String,
    val type: DeviceType,
    open val meta: RemoteDeviceMeta?
) {
    abstract fun asRemoteModel(): RemoteDevice<*>
}