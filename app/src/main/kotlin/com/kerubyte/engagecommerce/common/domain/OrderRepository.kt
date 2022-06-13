package com.kerubyte.engagecommerce.common.domain

import com.kerubyte.engagecommerce.common.util.Result

interface OrderRepository {

    suspend fun createOrder(userOrder: Map<String, Any>): Result<Any>
}