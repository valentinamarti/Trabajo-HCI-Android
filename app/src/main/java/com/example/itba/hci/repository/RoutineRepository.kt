package com.example.itba.hci.repository

import com.example.itba.hci.model.Routine
import com.example.itba.hci.remote.RoutineRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutineRepository(
    private val remoteDataSource: RoutineRemoteDataSource
) {
    private val routinesMutex = Mutex()
    private var routines: List<Routine> = emptyList()

    private suspend fun updateCache(routines: List<Routine>) {
        routinesMutex.withLock {
            this.routines = routines
        }
    }

    suspend fun getRoutines(refresh: Boolean = false): List<Routine> {
        if (refresh || routines.isEmpty()) {
            val result = remoteDataSource.getRoutines()
            updateCache(result.map { it.asModel() })
        }

        return routinesMutex.withLock { this.routines }
    }

    suspend fun getRoutine(routineId: String): Routine {
        return remoteDataSource.getRoutine(routineId).asModel()
    }

    suspend fun executeRoutine(routineId: String): Boolean {
        val result = remoteDataSource.executeRoutine(routineId)
        updateCache(emptyList())
        return result
    }

}







