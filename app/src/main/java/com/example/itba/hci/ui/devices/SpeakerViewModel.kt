package com.example.itba.hci.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itba.hci.model.Error
import com.example.itba.hci.DataSourceException
import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Speaker
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

class SpeakerViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpeakerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        collectOnViewModelScope(
            repository.currentDevice
                .filterIsInstance<Speaker>()
        ) { state, response -> state.copy(currentDevice = response as Speaker?) }

    }

    fun getDevice(deviceId: String) {
        Log.d("SpeakerViewModel", "Fetching device with ID: $deviceId")
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response ->
                val device = response as? Speaker
                Log.d("SpeakerViewModel", "Fetched device: $response")
                Log.d("SpeakerViewModel", "Current device: $device")
                state.copy(currentDevice = device)
            }
        )
    }

    fun nextSong() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.NEXT_SONG_ACTION) },
        { state, _ -> state }
    )

    fun previousSong() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.PREVIOUS_SONG_ACTION) },
        { state, _ -> state }
    )

    fun play() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.PLAY_ACTION) },
        { state, _ -> state }
    )

    fun stop() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.STOP_ACTION) },
        { state, _ -> state }
    )

    fun pause() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.PAUSE_ACTION) },
        { state, _ -> state }
    )

    fun resume() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Speaker.RESUME_ACTION) },
        { state, _ -> state }
    )

    fun setVolume(level: Int) = runOnViewModelScope(
        {
            Log.d("setVolume", "Setting volume to $level")
            val parameters = arrayOf<Any>(level)
            repository.executeDeviceAction(
                uiState.value.currentDevice?.id!!,
                Speaker.SET_VOLUME,
                parameters
            )
        },
        { state, _ -> state }
    )

    fun setGenre(genre: String) = runOnViewModelScope(
        {
            Log.d("setGenre", "Setting genre to $genre")
            val parameters = arrayOf<Any>(genre)
            repository.executeDeviceAction(
                uiState.value.currentDevice?.id!!,
                Speaker.SET_GENRE,
                parameters
            )
        },
        { state, _ -> state }
    )

    private fun modifyRoutine(device: Device) {
        runOnViewModelScope(
            { device.id?.let { repository.modifyDevice(device) } },
            { state, _ -> state.copy(currentDevice = device as Speaker) }
        )
    }

    fun updateFav(deviceId: String) {
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, currentDevice ->
                try {
                    currentDevice.meta?.favorite = !currentDevice.meta?.favorite!!
                    modifyRoutine(currentDevice)
                    state.copy(currentDevice = currentDevice as Speaker)
                } catch (e: Exception) {
                    Log.e("RoutineViewModel", "Error updating favorite state for routine $deviceId", e)
                    state // Return original state on error
                }
            }
        )
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (SpeakerUiState, T) -> SpeakerUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (SpeakerUiState, R) -> SpeakerUiState
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
