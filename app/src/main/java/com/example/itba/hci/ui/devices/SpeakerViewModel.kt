package com.example.itba.hci.ui.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itba.hci.model.Error
import com.example.itba.hci.DataSourceException
import com.example.itba.hci.model.Speaker
import com.example.itba.hci.repository.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class SpeakerViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpeakerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.currentDevice.collect { device ->
                _uiState.value = _uiState.value.copy(currentDevice = device as Speaker?)
            }
        }
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