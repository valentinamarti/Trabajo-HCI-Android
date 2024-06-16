package com.example.itba.hci.repository

import com.example.itba.hci.model.Device
import com.example.itba.hci.model.Lamp
import com.example.itba.hci.remote.DeviceRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//import ar.edu.itba.example.api.model.Device
//import ar.edu.itba.example.api.model.Lamp
//import ar.edu.itba.example.api.remote.DeviceRemoteDataSource
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map

class DeviceRepository(
    private val remoteDataSource: DeviceRemoteDataSource
) {
    val devices: Flow<List<Device>> =
        remoteDataSource.devices
            .map { it.map { jt -> jt.asModel() } }

    val currentDevice = devices.map { it.firstOrNull { jt -> jt is Lamp } }

    suspend fun getDevice(deviceId: String): Device {
        return remoteDataSource.getDevice(deviceId).asModel()
    }

    suspend fun addDevice(device: Device): Device {
        return remoteDataSource.addDevice(device.asRemoteModel()).asModel()
    }

    suspend fun modifyDevice(device: Device): Boolean {
        return remoteDataSource.modifyDevice(device.asRemoteModel())
    }

    suspend fun deleteDevice(deviceId: String): Boolean {
        return remoteDataSource.deleteDevice(deviceId)
    }

    suspend fun executeDeviceAction(
        deviceId: String,
        action: String,
        parameters: Array<*> = emptyArray<Any>()
    ): Array<*> {
        return remoteDataSource.executeDeviceAction(deviceId, action, parameters)
    }
}