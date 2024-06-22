package com.example.itba.hci.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itba.hci.model.Error
import com.example.itba.hci.DataSourceException
import com.example.itba.hci.model.Blind
import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Door
import com.example.itba.hci.model.Fridge
import com.example.itba.hci.model.Lamp
import com.example.itba.hci.model.Routine
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
class BlindViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BlindUiState())
    val uiState = _uiState.asStateFlow()

    init {
        collectOnViewModelScope(
            repository.currentDevice
                .filterIsInstance<Blind>()
        ) { state, response -> state.copy(currentDevice = response as Blind?) }

    }

    fun open() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Blind.OPEN_ACTION) },
        { state, _ -> state }
    )

    fun close() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Blind.CLOSE_ACTION) },
        { state, _ -> state }
    )

    fun setLevel(level: Int) = runOnViewModelScope(
        {
            Log.d("parameters 1", "")
            val parameters = arrayOf<Any>(level)
            Log.d("parameters", "${parameters}")
            repository.executeDeviceAction(
                uiState.value.currentDevice?.id!!,
                Blind.SET_LEVEL,
                parameters
            )
        },
        { state, _ -> state }
    )

    fun getDevice(deviceId: String) {
        Log.d("DoorViewModel", "Fetching device with ID: $deviceId")
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response ->
                val device = response as? Blind
                Log.d("DoorViewModel", "Fetched device: $response")
                Log.d("DoorViewModel", "Current device: $device")
                state.copy(currentDevice = device)
            }
        )
    }

    private fun modifyRoutine(device: Device) {
        runOnViewModelScope(
            { device.id?.let { repository.modifyDevice(device) } },
            { state, _ -> state.copy(currentDevice = device as Blind) }
        )
    }

    fun updateFav(deviceId: String) {
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, currentDevice ->
                try {
                    currentDevice.meta?.favorite = !currentDevice.meta?.favorite!!
                    modifyRoutine(currentDevice)
                    state.copy(currentDevice = currentDevice as Blind)
                } catch (e: Exception) {
                    Log.e("RoutineViewModel", "Error updating favorite state for routine $deviceId", e)
                    state // Return original state on error
                }
            }
        )
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (BlindUiState, T) -> BlindUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (BlindUiState, R) -> BlindUiState
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