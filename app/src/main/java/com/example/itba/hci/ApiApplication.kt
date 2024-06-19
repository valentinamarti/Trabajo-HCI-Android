package com.example.itba.hci

import android.app.Application
import com.example.itba.hci.repository.RoomRepository
import com.example.itba.hci.remote.DeviceRemoteDataSource
import com.example.itba.hci.remote.RoomRemoteDataSource
import com.example.itba.hci.remote.RoutineRemoteDataSource
import com.example.itba.hci.remote.api.RetrofitClient
import com.example.itba.hci.repository.DeviceRepository
import com.example.itba.hci.repository.RoutineRepository

class ApiApplication  : Application() {

    private val roomRemoteDataSource: RoomRemoteDataSource
        get() = RoomRemoteDataSource(RetrofitClient.roomService)

    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    private val routineRemoteDataSource: RoutineRemoteDataSource
        get() = RoutineRemoteDataSource(RetrofitClient.routineService)


    val roomRepository: RoomRepository
        get() = RoomRepository(roomRemoteDataSource)

    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)

    val routineRepository: RoutineRepository
        get() = RoutineRepository(routineRemoteDataSource)
}