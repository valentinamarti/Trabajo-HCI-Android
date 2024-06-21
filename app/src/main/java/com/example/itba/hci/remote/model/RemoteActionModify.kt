package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteActionModify {

    @SerializedName("device")
    var device: RemoteRoutineDeviceModify? = null

    @SerializedName("actionName")
    var actionName: String? = null

    @SerializedName("params")
    var params: ArrayList<RemoteParam> = arrayListOf()

}