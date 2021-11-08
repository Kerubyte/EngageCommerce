package com.kerubyte.engagecommerce.infrastructure.remote

import com.kerubyte.engagecommerce.data.repository.OrderRepository
import com.kerubyte.engagecommerce.infrastructure.util.Result

class FakeOrderRepository : OrderRepository {

    override suspend fun createOrder(userOrder: Map<String, Any>): Result<Nothing> {
        return Result.Success(null)
    }
}