package com.kerubyte.engagecommerce.common.domain.model

data class User(
    val uid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val cart: List<String>,
    val address: Map<String, String>
)
