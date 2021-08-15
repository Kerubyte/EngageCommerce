package com.kerubyte.engagecommerce.domain.model.local

data class User(
    val uid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val cart: List<Product>
)
