package com.example.itba.hci.repository

import android.util.Log
import com.example.itba.hci.model.Routine
import com.example.itba.hci.remote.RoutineRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoutineRepository(
    private val remoteDataSource: RoutineRemoteDataSource
) {
    val routines: Flow<List<Routine>> =
        remoteDataSource.routines
            .map { remoteRoutine ->
                val routines = remoteRoutine.filterNotNull().map { it.asModel() }
                Log.d("RoutineRepository", "Routines transformed successfully. Routines count: ${routines.size}")
                routines
            }



//    private val routinesMutex = Mutex()
//    private var routines: List<Routine> = emptyList()
//
//    private suspend fun updateCache(routines: List<Routine>) {
//        routinesMutex.withLock {
//            this.routines = routines
//        }
//    }
//
//    suspend fun getRoutines(refresh: Boolean = false): List<Routine> {
//        if (refresh || routines.isEmpty()) {
//            Log.d("RoutineRepository", "Fetching routines from remote data source")
//            val result = remoteDataSource.getRoutines()
//            Log.d("RoutineRepository", "Fetched routines: $result")
//            updateCache(result.map { it.asModel() })
//        } else {
//            Log.d("RoutineRepository", "Using cached routines")
//        }
//
//        return routinesMutex.withLock {
//            Log.d("RoutineRepository", "Returning routines: $routines")
//            this.routines
//        }
//    }

    suspend fun getRoutine(routineId: String): Routine {
        return remoteDataSource.getRoutine(routineId).asModel()
    }

    suspend fun executeRoutine(routineId: String): Boolean {
        val result = remoteDataSource.executeRoutine(routineId)
//        updateCache(emptyList())
        return result
    }

    suspend fun modifyRoutine(routine: Routine): Boolean {
        return remoteDataSource.modifyRoutine(routine.id, routine.asRemoteModifyModel())
    }

}







