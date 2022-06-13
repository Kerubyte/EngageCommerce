package com.kerubyte.engagecommerce.common.data.entity

data class UserEntity(
    val uid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val cart: List<String>? = null,
    val address: Map<String, String>? = null
)
