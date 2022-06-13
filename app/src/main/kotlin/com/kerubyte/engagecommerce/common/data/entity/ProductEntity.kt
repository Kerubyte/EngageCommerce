package com.kerubyte.engagecommerce.common.data.entity

data class ProductEntity(
    val uid: String? = null,
    val name: String? = null,
    val brand: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val delivery: Boolean = false
)
