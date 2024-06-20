package com.example.itba.hci.remote.model

import com.example.itba.hci.remote.RemoteColorPS
import com.google.gson.annotations.SerializedName

class RemoteDeviceMeta {
    @SerializedName("color")
    lateinit var color: RemoteColorPS

    @SerializedName("favorite")
    var favourite: Boolean = false
}