package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDevice
import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteDeviceModify
import com.example.itba.hci.remote.model.RemoteRoutine
import com.example.itba.hci.remote.model.RemoteRoutineMeta


abstract class Device(
    val id: String?,
    val name: String,
    val type: DeviceType,
    open val meta: RemoteDeviceMeta?
) {
    abstract fun asRemoteModel(): RemoteDevice<*>

    fun asRemoteModifyModel(): RemoteDeviceModify {
        val model = RemoteDeviceModify()
        model.name = name
        model.meta = meta!!

        return model
    }

}