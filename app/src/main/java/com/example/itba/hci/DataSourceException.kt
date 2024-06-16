package com.example.itba.hci

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)