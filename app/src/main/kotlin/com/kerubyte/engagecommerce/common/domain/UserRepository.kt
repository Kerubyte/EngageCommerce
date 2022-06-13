package com.kerubyte.engagecommerce.common.domain

import com.google.firebase.firestore.DocumentReference
import com.kerubyte.engagecommerce.common.domain.model.User
import com.kerubyte.engagecommerce.common.util.Result

interface UserRepository {

    fun getCurrentUser(): DocumentReference?

    suspend fun getUserData(): Result<User>

    suspend fun addToCart(productUid: String): Result<Nothing>

    suspend fun removeFromCart(productUid: String): Result<Nothing>

    suspend fun clearUserCart(): Result<Nothing>

    suspend fun updateAddress(userAddress: Map<String, String>): Result<Nothing>
}