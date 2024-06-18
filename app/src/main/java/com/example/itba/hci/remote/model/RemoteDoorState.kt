package com.example.itba.hci.remote.model

import com.google.gson.annotations.SerializedName

class RemoteDoorState {
    @SerializedName("status")
    lateinit var status: String
}