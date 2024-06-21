package com.example.itba.hci.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RemoteDeviceModify {
    @SerializedName("name")
    lateinit var name: String

    @SerializedName("meta")
    lateinit var meta: RemoteDeviceMeta
}