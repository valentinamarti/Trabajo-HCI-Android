package ar.edu.itba.example.api

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)