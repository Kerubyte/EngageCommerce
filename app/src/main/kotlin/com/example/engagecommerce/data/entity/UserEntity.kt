package com.example.engagecommerce.data.entity

import com.example.engagecommerce.domain.model.Product

data class UserEntity(
    val uid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val cart: List<Product>? = null
)
