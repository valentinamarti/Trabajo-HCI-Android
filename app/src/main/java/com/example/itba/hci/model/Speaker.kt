package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteSpeaker
import com.example.itba.hci.remote.model.RemoteSpeakerSong
import com.example.itba.hci.remote.model.RemoteSpeakerState

class Speaker(
    id: String?,
    name: String,
    val room: Room?,
    val status: String,
    val genre: String,
    val volume: Int,
    val song: RemoteSpeakerSong?,
    override val meta: RemoteDeviceMeta?
) : Device(id, name, DeviceType.SPEAKER, meta) {

    override fun asRemoteModel(): RemoteSpeaker {
        val state = RemoteSpeakerState()
        state.status = status
        state.genre = genre
        state.volume = volume
        song?.let {
            state.song?.title = it.title
            state.song?.artist = it.artist
            state.song?.album = it.album
            state.song?.duration = it.duration
            state.song?.progress = it.progress
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
