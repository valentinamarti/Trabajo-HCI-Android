package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Fridge


class RemoteFridge : RemoteDevice<RemoteFridgeState>() {

    override fun asModel(): Fridge {
        return Fridge(
            id = id,
            name = name,
            room = room?.asModel(),
            freezerTemperature = state.freezerTemperature,
            temperature = state.temperature,
            mode = state.mode,
            meta = meta
        )
    }
}
