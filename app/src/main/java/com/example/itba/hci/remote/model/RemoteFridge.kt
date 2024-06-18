package com.example.itba.hci.remote.model

import com.example.itba.hci.model.Fridge


class RemoteFridge : RemoteDevice<RemoteFridgeState>() {

    override fun asModel(): Fridge {
        return Fridge(
            id = id,
            name = name,
            room = room?.asModel(),
            status = RemoteStatus.asModel(state.status),
            freezerTemp = state.freezerTemp,
            fridgeTemp = state.fridgeTemp,
            mode = state.mode,
            meta = meta
        )
    }
}
