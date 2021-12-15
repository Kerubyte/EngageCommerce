package com.kerubyte.engagecommerce.infrastructure.remote

import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.util.Result
import com.kerubyte.engagecommerce.infrastructure.util.currentUserTest

class FakeUserRepository : UserRepository {

    private var shouldReturnError = false

    fun decideShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(email: String, password: String): Result<Nothing> {
        return Result.Success(null)
    }

    override suspend fun getUserData(): Result<User> {
        return if (shouldReturnError) Result.Error.AuthenticationError(null)
        else Result.Success(currentUserTest)
    }

    override suspend fun addToCart(productUid: String): Result<Nothing> {
        currentUserTest.cart.add(productUid)
        return Result.Success(null)
    }

    override suspend fun removeFromCart(productUid: String): Result<Nothing> {
        currentUserTest.cart.remove(productUid)
        return Result.Success(null)
    }

    override suspend fun clearUserCart(): Result<Nothing> {
        currentUserTest.cart.clear()
        return Result.Success(null)
    }

    override suspend fun updateAddress(userAddress: Map<String, String>): Result<Nothing> {
        TODO("Not yet implemented")
    }
}