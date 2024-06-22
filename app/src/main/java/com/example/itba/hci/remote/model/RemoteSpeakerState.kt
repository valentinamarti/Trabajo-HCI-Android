package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteSpeakerState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("genre")
    lateinit var genre: String

    @SerializedName("volume")
    var volume: Int = 0

    @SerializedName("song")
    var song: RemoteSpeakerSong? = null
}
