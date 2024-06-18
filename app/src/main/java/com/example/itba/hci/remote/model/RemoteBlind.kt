package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Blind

class RemoteBlind : RemoteDevice<RemoteBlindState>() {

    override fun asModel(): Blind {
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