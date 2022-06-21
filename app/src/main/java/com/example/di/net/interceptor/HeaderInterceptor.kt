package com.example.di.net.interceptor

import com.example.livescoredemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .header(KEY_NAME, BuildConfig.LIVE_SCORE_API_KEY)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val KEY_NAME = "x-apisports-key"
    }
}