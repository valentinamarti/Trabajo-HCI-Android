package com.example.itba.hci.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtil {
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Any::class.java, AnyTypeAdapter())
        .create()
}
