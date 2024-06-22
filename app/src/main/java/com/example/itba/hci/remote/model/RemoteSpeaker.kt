package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Speaker

class RemoteSpeaker : RemoteDevice<RemoteSpeakerState>() {

    override fun asModel(): Speaker {
        val song = state.song?.let {
            if (it.title != null && it.artist != null && it.album != null && it.duration != null && it.progress != null) {
                RemoteSpeakerSong(
                    title = it.title,
                    artist = it.artist,
                    album = it.album,
                    duration = it.duration,
                    progress = it.progress
                )
            } else {
                null
            }
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
