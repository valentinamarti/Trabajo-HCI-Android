package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Lamp

class RemoteLamp : RemoteDevice<RemoteLampState>() {

    override fun asModel(): Lamp {
        return Lamp(
            id = id,
            name = name,
            room = room?.asModel(),
            status = RemoteStatus.asModel(state.status),
            color = state.color,
            brightness = state.brightness,
            meta = meta
        )
    }
}