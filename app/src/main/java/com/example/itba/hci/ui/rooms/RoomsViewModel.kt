package ar.edu.itba.example.api.ui.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.example.api.DataSourceException
import ar.edu.itba.example.api.model.Error
import ar.edu.itba.example.api.model.Room
import ar.edu.itba.example.api.repository.RoomRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomsViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoomsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getRooms()
    }

    fun getRooms() = runOnViewModelScope(
        { repository.getRooms(true) },
        { state, response -> state.copy(rooms = response) }
    )

    fun getRoom(roomId: String) {
        runOnViewModelScope(
            { repository.getRoom(roomId) },
            { state, response -> state.copy(currentRoom = response) }
        )
    }

    fun addRoom(room: Room) {
        runOnViewModelScope(
            { repository.addRoom(room) },
            { state, response -> state.copy(currentRoom = response) }
        ).invokeOnCompletion { getRooms() }
    }

    fun modifyRoom(room: Room) {
        runOnViewModelScope(
            { repository.modifyRoom(room) },
            { state, _ -> state.copy(currentRoom = room) }
        ).invokeOnCompletion { getRooms() }
    }

    fun deleteRoom(roomId: String) = viewModelScope.launch {
        runOnViewModelScope(
            { repository.deleteRoom(roomId) },
            { state, _ -> state.copy(currentRoom = null) }
        ).invokeOnCompletion { getRooms() }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (RoomsUiState, R) -> RoomsUiState
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