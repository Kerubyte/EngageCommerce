package com.kerubyte.engagecommerce.feature.auth.domain

import com.kerubyte.engagecommerce.common.util.Result

interface AuthRepository {

    suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Any>

    suspend fun loginUser(email: String, password: String): Result<Any>
}