package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteAction{

    @SerializedName("device")
    var device: RemoteDevice<*>? = null

    @SerializedName("actionName")
    var actionName: String? = null

    @SerializedName("params")
    var params: ArrayList<RemoteParam> = arrayListOf()


    fun asRemoteActionModifyModel(): RemoteActionModify {
        val model = RemoteActionModify()
        model.actionName = actionName
        model.device = device?.asRemoteRoutineDeviceModifyModel()
        model.params = params

        return model
    }

}
