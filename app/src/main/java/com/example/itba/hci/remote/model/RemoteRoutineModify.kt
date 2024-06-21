package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Routine
import com.google.gson.annotations.SerializedName

class RemoteRoutineModify {

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("actions" )
    var actions : ArrayList<RemoteActionModify> = arrayListOf()

    @SerializedName("meta")
    lateinit var meta: RemoteRoutineMeta

}