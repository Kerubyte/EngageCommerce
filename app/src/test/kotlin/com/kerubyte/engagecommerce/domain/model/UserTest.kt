package com.kerubyte.engagecommerce.domain.model

data class User(
    val uid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val cart: MutableList<String>,
    val address: Map<String, String>
)
