package com.example.di.net

import com.example.di.net.main.ApiExecutor
import com.example.di.net.main.ApiExecutorImpl
import com.example.di.net.main.Apis
import com.example.di.net.interceptor.HeaderInterceptor
import com.example.livescoredemo.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLiveScoreApi(retrofit: Retrofit.Builder): Apis {
        return retrofit.baseUrl(BuildConfig.LIVE_SCORE_BASE_URL).build().create(Apis::class.java)
    }

    @Singleton
    @Provides
    fun provideApiExecutor(apiExecutorImpl: ApiExecutorImpl): ApiExecutor {
        return apiExecutorImpl
    }

    @Singleton
    @Provides
    fun provideClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        val time = 60L
        return OkHttpClient().newBuilder()
            .readTimeout(time, TimeUnit.SECONDS)
            .writeTimeout(time, TimeUnit.SECONDS)
            .connectTimeout(time, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder().apply {
            addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().serializeNulls().create())
            )
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(client)
        }
}