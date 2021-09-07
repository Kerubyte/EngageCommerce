package com.kerubyte.engagecommerce.domain.repo

import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.domain.model.Product

interface ProductRepository {

    suspend fun getAllProducts(): Resource<List<Product>>

    suspend fun getSingleProduct(productUid: String): Resource<Product>

    suspend fun getProductsFromCart(cartList: List<String>): Resource<List<Product>>
}