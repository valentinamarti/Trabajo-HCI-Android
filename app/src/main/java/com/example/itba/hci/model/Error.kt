package com.example.itba.hci.model

data class Error(
    val code: Int?,
    val message: String,
    val description: List<String>? = null
)