package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.util.Status

interface UserRepository {

    suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<Status>

    suspend fun loginUser(email: String, password: String): Resource<Status>

    suspend fun getUserData(): Resource<User>

    suspend fun addToCart(productUid: String): Resource<Status>

    suspend fun removeFromCart(productUid: String): Resource<Status>

    suspend fun clearUserCart(): Resource<Status>

    suspend fun updateAddress(userAddress: Map<String, String>): Resource<Status>
}