package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.infrastructure.util.Result

interface ProductRepository {

    suspend fun getAllProducts(): Result<List<Product>>

    suspend fun getSingleProduct(productUid: String): Result<Product>

    suspend fun getProductsFromCart(cartList: List<String>): Result<List<Product>>
}