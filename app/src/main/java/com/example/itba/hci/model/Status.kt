package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteStatus

enum class Status {
    ON, OFF;

    companion object {
        fun asRemoteModel(value: Status): String {
            return when (value) {
                ON -> RemoteStatus.ON
                else -> RemoteStatus.OFF
            }
        }
    }
}