package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Routine
import com.google.gson.annotations.SerializedName

class RemoteRoutine {



    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("meta")
    lateinit var meta: RemoteRoutineMeta

    fun asModel() : Routine {
        return Routine(
            id = id,
            name = name,
            description = meta.description,
            color = meta.color,
            favourite = meta.favourite
        )
    }
}