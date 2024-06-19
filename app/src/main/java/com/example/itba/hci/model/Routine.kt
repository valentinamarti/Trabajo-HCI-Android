package com.example.itba.hci.model

import com.example.itba.hci.remote.RemoteColorPS
import com.example.itba.hci.remote.model.RemoteAction
import com.example.itba.hci.remote.model.RemoteRoutine
import com.example.itba.hci.remote.model.RemoteRoutineMeta

class Routine(
    var id: String? = null,
    var name: String,
    var description: String,
    var actions: ArrayList<RemoteAction> = arrayListOf(),
    var color: RemoteColorPS,
    var favourite : String
) {

    fun asRemoteModel(): RemoteRoutine {
        val meta = RemoteRoutineMeta()
        meta.description = description
        meta.color = color
        meta.favourite = favourite

        val model = RemoteRoutine()
        model.id = id
        model.name = name
        model.actions = actions
        model.meta = meta

        return model
    }
}