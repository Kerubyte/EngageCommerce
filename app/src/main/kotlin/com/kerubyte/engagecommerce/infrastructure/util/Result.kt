package com.kerubyte.engagecommerce.infrastructure.util

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(data)
    sealed class Error<T>(message: String?) : Result<T>(null, message) {
        class NetworkError<T>(message: String?) : Error<T>(message)
        class AuthenticationError<T>(message: String?) : Error<T>(message)
    }
}
