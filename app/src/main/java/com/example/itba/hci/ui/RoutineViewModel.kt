package com.example.itba.hci.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itba.hci.DataSourceException
import com.example.itba.hci.model.Door
import com.example.itba.hci.model.Error
import com.example.itba.hci.model.Routine
import com.example.itba.hci.repository.RoutineRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutineViewModel(
    private val repository: RoutineRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoutinesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.routines.collect { routines ->
                _uiState.value = _uiState.value.copy(routines = routines)
            }
        }
    }

    fun getRoutine(routineId: String) {
        Log.d("RoutineViewModel", "Fetching routine with ID: $routineId")
        runOnViewModelScope(
            { repository.getRoutine(routineId) },
            { state, response ->
                Log.d("RoutineViewModel", "Fetched routine: $response")
                state.copy(currentRoutine = response) }
        )
    }
    fun executeRoutine(routine: Routine) {
        runOnViewModelScope(
            { routine.id?.let { repository.executeRoutine(it) } },
            { state, _ -> state.copy(currentRoutine = routine) }
        )
    }

    private fun modifyRoutine(routine: Routine) {
        runOnViewModelScope(
            { routine.id?.let { repository.modifyRoutine(routine) } },
            { state, _ -> state.copy(currentRoutine = routine) }
        )
    }

    fun updateFav(routineId: String) {
        runOnViewModelScope(
            { repository.getRoutine(routineId) },
            { state, currentRoutine ->
                try {
                    currentRoutine.favorite = !currentRoutine.favorite
                    modifyRoutine(currentRoutine)
                    state.copy(currentRoutine = currentRoutine)
                } catch (e: Exception) {
                    Log.e("RoutineViewModel", "Error updating favorite state for routine $routineId", e)
                    state // Return original state on error
                }
            }
        )
    }


    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (RoutinesUiState, R) -> RoutinesUiState
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






