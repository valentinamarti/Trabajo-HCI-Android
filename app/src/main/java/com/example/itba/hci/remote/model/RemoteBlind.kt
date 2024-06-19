package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Blind
import com.example.itba.hci.model.Device

class RemoteBlind : RemoteDevice<RemoteBlindState>() {

    override fun asModel(): Device {
        return Blind(
            id = id,
            name = name,
            room = room?.asModel(),
            status = RemoteStatus.asModel(state.status),
            level = state.level,
            meta = meta
        )
    }
}