package com.example.di.net.main.model


data class Response(
    val account: Account,
    val requests: Requests,
    val subscription: Subscription
)