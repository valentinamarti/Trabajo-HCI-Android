package com.example.itba.hci.ui

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.itba.hci.ui.devices.LampViewModel
import com.example.itba.hci.ApiApplication
import com.example.itba.hci.repository.DeviceRepository
import com.example.itba.hci.repository.RoomRepository
import com.example.itba.hci.repository.RoutineRepository
import com.example.itba.hci.ui.devices.DevicesViewModel

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as ApiApplication)
    val roomRepository = application.roomRepository
    val deviceRepository = application.deviceRepository
    val routineRepository = application.routineRepository
    return ViewModelFactory(
        roomRepository,
        deviceRepository,
        routineRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}

class ViewModelFactory (
    private val roomRepository: RoomRepository,
    private val deviceRepository: DeviceRepository,
    private val routineRepository: RoutineRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
//            isAssignableFrom(RoomsViewModel::class.java) ->
//                RoomsViewModel(roomRepository)

            isAssignableFrom(DevicesViewModel::class.java) ->
                DevicesViewModel(deviceRepository)

            isAssignableFrom(LampViewModel::class.java) ->
                LampViewModel(deviceRepository)

            isAssignableFrom(RoutineViewModel::class.java) ->
                RoutineViewModel(routineRepository)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}