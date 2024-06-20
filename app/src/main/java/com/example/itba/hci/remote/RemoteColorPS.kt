package com.example.itba.hci.remote

import com.example.itba.hci.remote.model.RemoteDevice
import com.google.gson.annotations.SerializedName

class RemoteColorPS {
    @SerializedName("primaryColor")
    var primary: String? = null

    @SerializedName("secondaryColor")
    var secondary: String? = null
}