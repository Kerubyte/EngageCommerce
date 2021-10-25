package com.kerubyte.engagecommerce.infrastructure.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    sealed class Error<T>(message: String?) : Resource<T>(null, message) {
        class NetworkError<T>(message: String?) : Error<T>(message)
        class AuthenticationError<T>(message: String?) : Error<T>(message)
    }
}
