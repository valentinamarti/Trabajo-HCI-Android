package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteRoutineMeta {
    @SerializedName("description")
    lateinit var description: String

    @SerializedName("color")
    lateinit var color: String

    @SerializedName("favourite")
    lateinit var favourite: String

}