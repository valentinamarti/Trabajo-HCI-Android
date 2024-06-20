package com.example.itba.hci.model

import com.example.itba.hci.remote.model.RemoteDeviceMeta
import com.example.itba.hci.remote.model.RemoteFridge
import com.example.itba.hci.remote.model.RemoteFridgeState

class Fridge(
    id: String?,
    name: String,
    val room: Room?,
    val freezerTemperature: Int,
    val temperature: Int,
    val mode: String,
    override val meta: RemoteDeviceMeta?
) : Device(id, name, DeviceType.FRIDGE, meta) {

    override fun asRemoteModel(): RemoteFridge {
        val state = RemoteFridgeState()
        state.freezerTemperature = freezerTemperature
        state.temperature = temperature
        state.mode = mode

        val model = RemoteFridge()
        model.id = id
        model.name = name
        model.room = room?.asRemoteModel()
        model.setState(state)
        if (meta != null) {
            model.meta = meta
        }
        return model
    }

}

