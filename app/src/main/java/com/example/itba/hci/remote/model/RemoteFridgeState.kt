package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteFridgeState {
    @SerializedName("mode")
    lateinit var mode: String

    @SerializedName("freezerTemperature")
    var freezerTemperature: Int = -8

    @SerializedName("temperature")
    var temperature: Int = 2
}