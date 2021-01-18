package com.example.engagecommerce.data

data class Product(
    val uid: String? = null,
    val name: String? = null,
    val price: Long? = null,
    val description: String? = null,
    val details: List<String>? = null,
    val imageUrl: String? = null
)