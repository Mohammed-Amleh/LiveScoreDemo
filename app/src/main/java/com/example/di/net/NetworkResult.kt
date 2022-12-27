package com.example.di.net

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

sealed class NetworkResult<T : Any> {
    class ApiSuccess<T : Any>(val data: T) : NetworkResult<T>()
    class ApiError<T : Any>(val e: Throwable) : NetworkResult<T>()
}

suspend fun <T : Any> handleApiResponse(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Timber.d(body.toString())
                NetworkResult.ApiSuccess(body)
            } else {
                NetworkResult.ApiError(Throwable("${response.code()}: ${response.message()}"))
            }
        } catch (e: HttpException) {
            NetworkResult.ApiError(Throwable("${e.code()}: ${e.message}"))
        } catch (e: Throwable) {
            NetworkResult.ApiError(e)
        }
    }
}
