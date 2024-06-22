package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Speaker
import com.example.itba.hci.model.Song

class RemoteSpeaker : RemoteDevice<RemoteSpeakerState>() {

    override fun asModel(): Speaker {
        val song = if (state.title != null && state.artist != null && state.album != null &&
            state.duration != null && state.progress != null) {
            Song(
                title = state.title!!,
                artist = state.artist!!,
                album = state.album!!,
                duration = state.duration!!,
                progress = state.progress!!
            )
        } else {
            null
        }

        return Speaker(
            id = id,
            name = name,
            room = room?.asModel(),
            status = state.status,
            volume = state.volume,
            genre = state.genre,
            song = song,
            meta = meta
        )
    }
}
