package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Speaker

class RemoteSpeaker : RemoteDevice<RemoteSpeakerState>() {

    override fun asModel(): Device {
        return Speaker(
            id = id,
            name = name,
            room = room?.asModel(),
            status = RemoteStatus.asModel(state.status),
            volume = state.volume,
            genre = state.genre,
            meta = meta
        )
    }
}