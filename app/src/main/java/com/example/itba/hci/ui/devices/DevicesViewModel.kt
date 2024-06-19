package com.example.itba.hci.ui.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itba.hci.DataSourceException
import com.example.itba.hci.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.itba.hci.model.Error

class DevicesViewModel(
    repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DevicesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.devices.collect { devices ->
                _uiState.value = _uiState.value.copy(devices = devices)
                Log.d("DevicesViewModel", "UI state updated successfully. Devices count: ${devices.size}")
            }
        }
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (DevicesUiState, T) -> DevicesUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}