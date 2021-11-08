package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.util.Result

interface UserRepository {

    suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Nothing>

    suspend fun loginUser(email: String, password: String): Result<Nothing>

    suspend fun getUserData(): Result<User>

    suspend fun addToCart(productUid: String): Result<Nothing>

    suspend fun removeFromCart(productUid: String): Result<Nothing>

    suspend fun clearUserCart(): Result<Nothing>

    suspend fun updateAddress(userAddress: Map<String, String>): Result<Nothing>
}