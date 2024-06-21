package com.example.itba.hci.model

import com.example.itba.hci.remote.RemoteColorPS
import com.example.itba.hci.remote.model.RemoteAction
import com.example.itba.hci.remote.model.RemoteActionModify
import com.example.itba.hci.remote.model.RemoteDeviceModify
import com.example.itba.hci.remote.model.RemoteRoutine
import com.example.itba.hci.remote.model.RemoteRoutineMeta
import com.example.itba.hci.remote.model.RemoteRoutineModify

class Routine(
    var id: String? = null,
    var name: String,
    var actions: ArrayList<RemoteAction> = arrayListOf(),
    open val meta: RemoteRoutineMeta?
) {


    fun asRemoteModel(): RemoteRoutine {
        val model = RemoteRoutine()
        model.id = id
        model.name = name
        model.actions = actions
        model.meta = meta!!

        return model
    }


    fun asRemoteModifyModel(): RemoteRoutineModify {
        val model = RemoteRoutineModify()
        model.name = name
        model.actions = actions.map { it.asRemoteActionModifyModel() } as ArrayList<RemoteActionModify>
        model.meta = meta!!

        return model
    }
}