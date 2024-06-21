package com.example.itba.hci.remote

import android.util.Log
import com.example.itba.hci.model.Routine
import com.example.itba.hci.remote.api.RoutineService
import com.example.itba.hci.remote.model.RemoteDeviceModify
import com.example.itba.hci.remote.model.RemoteRoom
import com.example.itba.hci.remote.model.RemoteRoutine
import com.example.itba.hci.remote.model.RemoteRoutineModify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RoutineRemoteDataSource(
    private val routineService: RoutineService
): RemoteDataSource() {

    val routines: Flow<List<RemoteRoutine>> = flow {
        while (true) {
            try {
                val routines = handleApiResponse {
                    routineService.getRoutines()
                }
                Log.d("RoutineRemoteDataSource", "Routines fetched successfully. Routines count: ${routines.size}")
                Log.d("RoutineRemoteDataSource", "Routines: $routines")
                emit(routines)
            } catch (t: Throwable) {
                Log.e("RoutineRemoteDataSource", "Error fetching Routines", t)
            }
            delay(DELAY)
        }
    }.catch { t: Throwable ->
        Log.e("RoutineRemoteDataSource", "Caught error in flow: $t", t)
    }

    suspend fun getRoutine(routineId: String): RemoteRoutine {
        return handleApiResponse {
            routineService.getRoutine(routineId)
        }
    }

    suspend fun executeRoutine(routineId: String): Boolean {
        return handleApiResponse {
            routineService.executeRoutine(routineId)
        }
    }

    suspend fun modifyRoutine(routineId: String?, routine: RemoteRoutineModify): Boolean {
        return handleApiResponse {
            routineService.modifyRoutine(routineId!!, routine)
        }
    }

    companion object {
        const val DELAY: Long = 10000
    }
}
