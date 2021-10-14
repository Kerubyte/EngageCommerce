package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status

interface OrderRepository {

    suspend fun createOrder(userOrder: Map<String, Any>): Resource<Status>
}