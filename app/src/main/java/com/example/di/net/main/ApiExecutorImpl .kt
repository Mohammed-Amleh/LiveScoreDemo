package com.example.di.net.main

import com.example.di.net.main.model.Status
import io.reactivex.Single
import javax.inject.Inject

class ApiExecutorImpl @Inject constructor(
    private val apis: Apis
) : ApiExecutor {
    override fun getStatus(): Single<Status> {
        return apis.getStatus()
    }
}