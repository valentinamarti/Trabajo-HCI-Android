package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteSpeakerState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("genre")
    lateinit var genre: String

    @SerializedName("volume")
    var volume: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("artist")
    var artist: String? = null

    @SerializedName("album")
    var album: String? = null

    @SerializedName("duration")
    var duration: String? = null

    @SerializedName("progress")
    var progress: String? = null
}
