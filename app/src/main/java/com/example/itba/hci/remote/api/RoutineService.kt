package com.example.itba.hci.remote.api

import com.example.itba.hci.remote.model.RemoteResult
import com.example.itba.hci.remote.model.RemoteRoutine
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoutineService {

    @GET("routines")
    suspend fun getRoutines(): Response<RemoteResult<List<RemoteRoutine>>>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") routineId: String): Response<RemoteResult<RemoteRoutine>>

    @PUT("routines/{roomId}/execute")
    suspend fun executeRoutine(
        @Path("routineId") routineId: String
    ): Response<RemoteResult<Boolean>>

}