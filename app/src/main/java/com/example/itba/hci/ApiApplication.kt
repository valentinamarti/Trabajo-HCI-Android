package com.example.itba.hci

import android.app.Application
import ar.edu.itba.example.api.repository.RoomRepository
import com.example.itba.hci.remote.DeviceRemoteDataSource
import com.example.itba.hci.remote.RoomRemoteDataSource
import com.example.itba.hci.remote.api.RetrofitClient
import com.example.itba.hci.repository.DeviceRepository

class ApiApplication  : Application() {

    private val roomRemoteDataSource: RoomRemoteDataSource
        get() = RoomRemoteDataSource(RetrofitClient.roomService)

    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    val roomRepository: RoomRepository
        get() = RoomRepository(roomRemoteDataSource)

    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)
}