package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteSpeaker
import com.example.itba.hci.remote.model.RemoteSpeakerState

class Speaker(
    id: String?,
    name: String,
    val room: Room?,
    val status: String,
    val genre: String,
    val volume: Int,
    val song: Song?,
    override val meta: RemoteDeviceMeta?
) : Device(id, name, DeviceType.SPEAKER, meta) {

    override fun asRemoteModel(): RemoteSpeaker {
        val state = RemoteSpeakerState()
        state.status = status
        state.genre = genre
        state.volume = volume
        song?.let {
            state.title = it.title
            state.artist = it.artist
            state.album = it.album
            state.duration = it.duration
            state.progress = it.progress
        }

        val model = RemoteSpeaker()
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
        const val PLAY_ACTION = "play"
        const val STOP_ACTION = "stop"
        const val PAUSE_ACTION = "pause"
        const val RESUME_ACTION = "stop"
        const val NEXT_SONG_ACTION = "nextSong"
        const val PREVIOUS_SONG_ACTION = "previousSong"
        const val SET_VOLUME = "setVolume"
    }
}

data class Song(
    val title: String,
    val artist: String,
    val album: String,
    val duration: String,
    val progress: String
)
