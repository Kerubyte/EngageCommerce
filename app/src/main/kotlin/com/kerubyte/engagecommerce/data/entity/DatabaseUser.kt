package com.kerubyte.engagecommerce.data.entity

data class DatabaseUser(
    val uid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val cart: List<String>? = null,
    val address: Map<String, String>? = null
)
