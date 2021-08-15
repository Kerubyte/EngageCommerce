package com.kerubyte.engagecommerce.data.entity

import com.kerubyte.engagecommerce.domain.model.Product

data class DatabaseUser(
    val uid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val cart: List<Product>? = null
)
