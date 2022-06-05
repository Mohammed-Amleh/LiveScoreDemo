package com.example.di.net.main.model


data class Status(
    val errors: List<Any>,
    val `get`: String,
    val parameters: List<Any>,
    val response: Response,
    val results: Int
)