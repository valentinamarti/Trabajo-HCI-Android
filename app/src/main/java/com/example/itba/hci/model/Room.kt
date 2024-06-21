package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteRoom

class Room(
    var id: String? = null,
    var name: String,
) {

    fun asRemoteModel(): RemoteRoom {
        val model = RemoteRoom()
        model.id = id
        model.name = name

        return model
    }
}