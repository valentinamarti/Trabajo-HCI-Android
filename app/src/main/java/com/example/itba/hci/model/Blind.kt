package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteBlind
import com.example.itba.hci.remote.model.RemoteBlindState

class Blind(
    id: String?,
    name: String,
    val room: Room?,
    val status: Status,
    val level: Int,
    override val meta: RemoteDeviceMeta? // Cambia el tipo a DeviceMeta
) : Device(id, name, DeviceType.BLIND, meta) { // Convierte DeviceMeta a RemoteDeviceMeta

    override fun asRemoteModel(): RemoteBlind {
        val state = RemoteBlindState()
        state.status = Status.asRemoteModel(status)
        state.level = level

        val model = RemoteBlind()
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
        const val OPEN_ACTION = "close"
        const val CLOSE_ACTION = "open"
    }
}
