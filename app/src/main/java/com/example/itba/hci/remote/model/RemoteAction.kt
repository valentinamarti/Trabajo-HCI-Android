package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteAction{

    @SerializedName("device")
    var device: RemoteDevice<*>? = null

    @SerializedName("actionName")
    var actionName: String? = null

    @SerializedName("params")
    var params: ArrayList<String> = arrayListOf()

}
