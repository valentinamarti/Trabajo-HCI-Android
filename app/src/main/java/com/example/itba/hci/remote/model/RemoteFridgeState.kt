package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteFridgeState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("mode")
    lateinit var mode: String

    @SerializedName("freezerTemperature")
    var freezerTemp: Int = -8

    @SerializedName("temperature")
    var fridgeTemp: Int = 2
}