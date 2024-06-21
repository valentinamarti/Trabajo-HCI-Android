package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteDoor
import com.example.itba.hci.remote.model.RemoteDoorState
import com.example.itba.hci.remote.model.RemoteFridgeState
import com.example.itba.hci.remote.model.RemoteLamp
import com.example.itba.hci.remote.model.RemoteLampState

class Door(
    id: String?,
    name: String,
    val room: Room?,
    val status: String,
    val lock: String,
    override val meta: RemoteDeviceMeta?
) : Device(id, name, DeviceType.DOOR, meta) {

    override fun asRemoteModel(): RemoteDoor {
        val state = RemoteDoorState()
        state.status = status
        state.lock = lock

        val model = RemoteDoor()
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
        const val LOCK_ACTION = "lock"
        const val UNLOCK_ACTION = "unlock"
        const val OPEN_ACTION = "open"
        const val CLOSE_ACTION = "close"
    }
}
