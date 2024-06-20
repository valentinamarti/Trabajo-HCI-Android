package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteDeviceType {
    @SerializedName("id")
    lateinit var id: String

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("powerUsage")
    var powerUsage: Int? = null

    companion object {
        const val LAMP_DEVICE_TYPE_ID = "go46xmbqeomjrsjr"
        const val FRIDGE_DEVICE_TYPE_ID = "rnizejqr2di0okho"
        const val DOOR_DEVICE_TYPE_ID = "lsf78ly0eqrjbz91"
        const val SPEAKER_DEVICE_TYPE_ID = "c89b94e8581855bc"
        const val BLIND_DEVICE_TYPE_ID = "eu0v2xgprrhhg41g"
    }
}