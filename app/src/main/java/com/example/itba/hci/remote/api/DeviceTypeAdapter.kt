package com.example.itba.hci.remote.api

import com.example.itba.hci.remote.model.RemoteBlind
import com.example.itba.hci.remote.model.RemoteDevice
import com.example.itba.hci.remote.model.RemoteDeviceType
import com.example.itba.hci.remote.model.RemoteDoor
import com.example.itba.hci.remote.model.RemoteFridge
import com.example.itba.hci.remote.model.RemoteLamp
import com.example.itba.hci.remote.model.RemoteSpeaker
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DeviceTypeAdapter : JsonDeserializer<RemoteDevice<*>?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RemoteDevice<*>? {
        val gson = Gson()
        val jsonDeviceObject = json.asJsonObject
        val jsonDeviceTypeObject = jsonDeviceObject["type"].asJsonObject
        val deviceTypeId = jsonDeviceTypeObject["id"].asString

        return when (deviceTypeId) {
            RemoteDeviceType.LAMP_DEVICE_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteLamp?>() {}.type)
            RemoteDeviceType.FRIDGE_DEVICE_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteFridge?>() {}.type)
            RemoteDeviceType.DOOR_DEVICE_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteDoor?>() {}.type)
            RemoteDeviceType.SPEAKER_DEVICE_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteSpeaker?>() {}.type)
            RemoteDeviceType.BLIND_DEVICE_TYPE_ID -> gson.fromJson(jsonDeviceObject, object : TypeToken<RemoteBlind?>() {}.type)
            else -> null
        }
    }
}