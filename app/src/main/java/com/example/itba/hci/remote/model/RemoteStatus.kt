package ar.edu.itba.example.api.remote.model

import com.example.itba.hci.model.Status

object RemoteStatus {
    const val ON = "on"
    const val OFF = "off"

    fun asModel(status: String): Status {
        return when (status) {
            ON -> Status.ON
            else -> Status.OFF
        }
    }
}