package com.kerubyte.engagecommerce.domain.model.database

import com.kerubyte.engagecommerce.domain.model.local.Product

data class DatabaseUser(
    val uid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val cart: List<Product>? = null
)
