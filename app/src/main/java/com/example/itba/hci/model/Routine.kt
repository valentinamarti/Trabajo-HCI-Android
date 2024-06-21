package com.example.itba.hci.model

import com.example.itba.hci.remote.RemoteColorPS
import com.example.itba.hci.remote.model.RemoteAction
import com.example.itba.hci.remote.model.RemoteActionModify
import com.example.itba.hci.remote.model.RemoteRoutine
import com.example.itba.hci.remote.model.RemoteRoutineMeta
import com.example.itba.hci.remote.model.RemoteRoutineModify

class Routine(
    var id: String? = null,
    var name: String,
    var description: String?,
    var actions: ArrayList<RemoteAction> = arrayListOf(),
    var color: RemoteColorPS,
    var favorite : Boolean
) {


    fun asRemoteModel(): RemoteRoutine {
        val meta = RemoteRoutineMeta()
        meta.description = description
        meta.color = color
        meta.favorite = favorite

        val model = RemoteRoutine()
        model.id = id
        model.name = name
        model.actions = actions
        model.meta = meta

        return model
    }


    fun asRemoteModifyModel(): RemoteRoutineModify {
        val meta = RemoteRoutineMeta()
        meta.description = description
        meta.color = color
        meta.favorite = favorite

        val model = RemoteRoutineModify()
        model.name = name
        model.actions = actions.map { it.asRemoteActionModifyModel() } as ArrayList<RemoteActionModify>
        model.meta = meta

        return model
    }
}