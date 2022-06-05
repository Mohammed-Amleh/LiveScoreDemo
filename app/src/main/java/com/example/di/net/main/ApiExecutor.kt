package com.example.di.net.main

import com.example.di.net.main.model.Status
import io.reactivex.Single

interface ApiExecutor {

    fun getStatus(): Single<Status>
}