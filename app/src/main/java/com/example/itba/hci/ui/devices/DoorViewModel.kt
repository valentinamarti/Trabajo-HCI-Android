package com.example.itba.hci.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itba.hci.model.Error
import com.example.itba.hci.DataSourceException
import com.example.itba.hci.model.Blind
import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Door
import com.example.itba.hci.repository.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class DoorViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoorUiState())
    val uiState = _uiState.asStateFlow()

    init {
        collectOnViewModelScope(
            repository.currentDevice
                .filterIsInstance<Door>()
        ) { state, response -> state.copy(currentDevice = response as Door?) }

    }

    fun open() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.OPEN_ACTION) },
        { state, _ -> state }
    )

    fun close() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.CLOSE_ACTION) },
        { state, _ -> state }
    )


    fun lock() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.LOCK_ACTION) },
        { state, _ -> state }
    )

    fun unlock() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Door.UNLOCK_ACTION) },
        { state, _ -> state }
    )
    fun getDevice(deviceId: String) {
        Log.d("DoorViewModel", "Fetching device with ID: $deviceId")
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response ->
                val device = response as? Door
                Log.d("DoorViewModel", "Fetched device: $response")
                Log.d("DoorViewModel", "Current device: $device")
                state.copy(currentDevice = device)
            }
        )
    }

    private fun modifyRoutine(device: Device) {
        runOnViewModelScope(
            { device.id?.let { repository.modifyDevice(device) } },
            { state, _ -> state.copy(currentDevice = device as Door) }
        )
    }

    fun updateFav(deviceId: String) {
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, currentDevice ->
                try {
                    currentDevice.meta?.favorite = !currentDevice.meta?.favorite!!
                    modifyRoutine(currentDevice)
                    state.copy(currentDevice = currentDevice as Door)
                } catch (e: Exception) {
                    Log.e("RoutineViewModel", "Error updating favorite state for routine $deviceId", e)
                    state // Return original state on error
                }
            }
        )
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (DoorUiState, T) -> DoorUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (DoorUiState, R) -> DoorUiState
    ): Job = viewModelScope.launch {
        _uiState.update { it.copy(loading = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { updateState(it, response).copy(loading = false) }
        }.onFailure { e ->
            _uiState.update { it.copy(loading = false, error = handleError(e)) }
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}