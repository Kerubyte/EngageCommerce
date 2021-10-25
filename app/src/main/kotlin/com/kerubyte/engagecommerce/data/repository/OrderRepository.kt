package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.infrastructure.util.Resource

interface OrderRepository {

    suspend fun createOrder(userOrder: Map<String, Any>): Resource<Nothing>
}