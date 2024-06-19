package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Door
import com.example.itba.hci.model.Lamp

class RemoteDoor : RemoteDevice<RemoteDoorState>() {

    override fun asModel(): Device {
        return Door(
            id = id,
            name = name,
            room = room?.asModel(),
            status = RemoteStatus.asModel(state.status),
            meta = meta
        )
    }
}