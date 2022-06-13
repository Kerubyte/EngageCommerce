package com.kerubyte.engagecommerce.common.domain

import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import com.kerubyte.engagecommerce.common.util.Result

interface ProductRepository {

    suspend fun getAllProducts(): Result<List<ProductModel>>

    suspend fun getSingleProduct(productUid: String): Result<ProductModel>

    suspend fun getProductsFromCart(cartList: List<String>): Result<List<ProductModel>>
}