package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Device
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


abstract class RemoteDevice<T : Any> {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("type")
    @Expose(serialize = false)
    lateinit var type: RemoteDeviceType

    @SerializedName("room")
    var room: RemoteRoom? = null

    @Expose(serialize = false)
    lateinit var state: T
        private set

    @SerializedName("meta")
    lateinit var meta: RemoteDeviceMeta

    fun setState(state: T) {
        this.state = state
    }

    abstract fun asModel(): Device
}