package com.example.engagecommerce.data.entity

data class UserEntity(
    val uid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val cart: List<String>? = null
)
