package com.example.di.net.main

import com.example.di.net.main.model.Status
import io.reactivex.Single
import retrofit2.http.GET

interface Apis {

    @GET("status")
    fun getStatus(): Single<Status>
}