package com.kerubyte.engagecommerce.common.domain

import com.kerubyte.engagecommerce.common.domain.model.Product
import com.kerubyte.engagecommerce.common.util.Result

interface ProductRepository {

    suspend fun getAllProducts(): Result<List<Product>>

    suspend fun getSingleProduct(productUid: String): Result<Product>

    suspend fun getProductsFromCart(cartList: List<String>): Result<List<Product>>
}