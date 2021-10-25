package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.util.Resource

interface UserRepository {

    suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<Nothing>

    suspend fun loginUser(email: String, password: String): Resource<Nothing>

    suspend fun getUserData(): Resource<User>

    suspend fun addToCart(productUid: String): Resource<Nothing>

    suspend fun removeFromCart(productUid: String): Resource<Nothing>

    suspend fun clearUserCart(): Resource<Nothing>

    suspend fun updateAddress(userAddress: Map<String, String>): Resource<Nothing>
}