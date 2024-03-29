package com.kerubyte.engagecommerce.common.domain.model

data class ProductModel(
    val uid: String = "",
    val name: String = "",
    val brand: String = "",
    val price: Double = 0.00,
    val formattedPrice: String = "",
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val delivery: Boolean = false
)
