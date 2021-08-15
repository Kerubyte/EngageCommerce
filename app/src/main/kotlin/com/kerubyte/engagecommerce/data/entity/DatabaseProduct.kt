package com.kerubyte.engagecommerce.data.entity

data class DatabaseProduct(
    val uid: String? = null,
    val name: String? = null,
    val brand: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val delivery: Boolean = false
)
