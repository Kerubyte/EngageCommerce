package com.example.engagecommerce.domain.model
data class Product(
    val uid: String = "",
    val name: String = "",
    val brand: String = "",
    val price: Long = 0L,
    val formattedPrice: String = "",
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val delivery: Boolean = false
)