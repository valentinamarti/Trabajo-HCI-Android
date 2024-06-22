package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteSpeakerSong(
    @SerializedName("title")
    var title: String?,

    @SerializedName("artist")
    var artist: String?,

    @SerializedName("album")
    var album: String?,

    @SerializedName("duration")
    var duration: String?,

    @SerializedName("progress")
    var progress: String?
)
