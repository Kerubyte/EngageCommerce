package com.kerubyte.engagecommerce.application.repo

import com.kerubyte.engagecommerce.application.utils.Resource
import com.kerubyte.engagecommerce.domain.model.local.Product

interface ProductRepository {

    suspend fun getAllProducts(): Resource<List<Product>>

}