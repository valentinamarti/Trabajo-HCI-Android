package com.example.itba.hci.remote.model

import com.example.itba.hci.remote.RemoteColorPS
import com.google.gson.annotations.SerializedName

class RemoteRoutineMeta {
    @SerializedName("description")
    var description: String? = ""

    @SerializedName("color")
    lateinit var color: RemoteColorPS

    @SerializedName("favorite")
    var favorite: Boolean = false

}