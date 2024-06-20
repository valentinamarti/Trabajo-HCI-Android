package com.example.itba.hci.remote.model

import com.example.itba.hci.remote.RemoteColorPS
import com.google.gson.annotations.SerializedName

class RemoteRoutineMeta {
    @SerializedName("description")
    var description: String? = null

    @SerializedName("color")
    lateinit var color: RemoteColorPS

    @SerializedName("favourite")
    var favourite: Boolean = false

}