package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteLamp
import com.example.itba.hci.remote.model.RemoteLampState

class Lamp(
    id: String?,
    name: String,
    val room: Room?,
    val status: Status,
    val color: String,
    val brightness: Int,
    override val meta: RemoteDeviceMeta? // Cambia el tipo a DeviceMeta
) : Device(id, name, DeviceType.LAMP, meta) { // Convierte DeviceMeta a RemoteDeviceMeta

    override fun asRemoteModel(): RemoteLamp {
        val state = RemoteLampState()
        state.status = Status.asRemoteModel(status)
        state.color = color
        state.brightness = brightness

        val model = RemoteLamp()
        model.id = id
        model.name = name
        model.room = room?.asRemoteModel()
        model.setState(state)
        if (meta != null) {
            model.meta = meta
        }

        return model
    }

    companion object {
        const val TURN_ON_ACTION = "turnOn"
        const val TURN_OFF_ACTION = "turnOff"
    }
}