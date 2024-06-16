package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteResult<T : Any> {
    @SerializedName("result")
    lateinit var result: T
}