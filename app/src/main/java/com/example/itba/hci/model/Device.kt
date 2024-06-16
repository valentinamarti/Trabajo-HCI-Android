package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDevice
import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteDeviceType

class Device(
    val id: String?,
    val name: String,
    val type: RemoteDeviceType,
    var color: String
) {
    fun asRemoteModel(): RemoteDevice {
        val meta = RemoteDeviceMeta()
        meta.color = color

        val model = RemoteDevice()
        model.id = id
        model.name = name
        model.type = type
        model.meta = meta

        return model
    }
}