package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.infrastructure.util.Result

interface OrderRepository {

    suspend fun createOrder(userOrder: Map<String, Any>): Result<Nothing>
}