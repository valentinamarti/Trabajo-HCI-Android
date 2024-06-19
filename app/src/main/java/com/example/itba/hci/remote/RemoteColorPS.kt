package com.example.itba.hci.remote

import com.example.itba.hci.remote.model.RemoteDevice
import com.google.gson.annotations.SerializedName

class RemoteColorPS {
    @SerializedName("primary")
    var primary: String? = null

    @SerializedName("secondary")
    var secondary: String? = null
}