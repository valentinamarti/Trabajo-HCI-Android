package com.example.itba.hci.remote

import android.util.Log
import com.example.itba.hci.remote.api.DeviceService
import com.example.itba.hci.remote.model.RemoteDevice
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeviceRemoteDataSource(
    private val deviceService: DeviceService
) : RemoteDataSource() {

    val devices: Flow<List<RemoteDevice<*>>> = flow {
        while (true) {
            try {
                val devices = handleApiResponse {
                    deviceService.getDevices()
                }
                Log.d("DeviceRemoteDataSource", "Devices fetched successfully. Devices count: ${devices.size}")
                Log.d("DeviceRemoteDataSource", "Devices: $devices")
                emit(devices)
            } catch (e: Exception) {
                Log.e("DeviceRemoteDataSource", "Error fetching devices", e)
            }
            delay(DELAY)
        }
    }

    suspend fun getDevice(deviceId: String): RemoteDevice<*> {
        return handleApiResponse {
            deviceService.getDevice(deviceId)
        }
    }

    suspend fun addDevice(device: RemoteDevice<*>): RemoteDevice<*> {
        return handleApiResponse {
            deviceService.addDevice(device)
        }
    }

    suspend fun modifyDevice(device: RemoteDevice<*>): Boolean {
        return handleApiResponse {
            deviceService.modifyDevice(device.id!!, device)
        }
    }

    suspend fun deleteDevice(deviceId: String): Boolean {
        return handleApiResponse {
            deviceService.deleteDevice(deviceId)
        }
    }

    suspend fun executeDeviceAction(
        deviceId: String,
        action: String,
        parameters: Array<*>
    ): Array<*> {
        return handleApiResponse {
            deviceService.executeDeviceAction(deviceId, action, parameters)
        }
    }

    companion object {
        const val DELAY: Long = 10000
    }
}