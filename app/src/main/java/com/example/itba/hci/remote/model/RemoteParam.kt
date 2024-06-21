package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteParam {
    @SerializedName("value")
    var value: Any? = null

    override fun toString(): String {
        return value?.toString() ?: "null"
    }
}