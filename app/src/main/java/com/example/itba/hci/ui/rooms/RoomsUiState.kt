package ar.edu.itba.example.api.ui.rooms

import ar.edu.itba.example.api.model.Error
import ar.edu.itba.example.api.model.Room

data class RoomsUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val rooms: List<Room> = emptyList(),
    val currentRoom: Room? = null
)

val RoomsUiState.canGetCurrent: Boolean get() = currentRoom != null
val RoomsUiState.canModify: Boolean get() = currentRoom != null
val RoomsUiState.canDelete: Boolean get() = canModify
