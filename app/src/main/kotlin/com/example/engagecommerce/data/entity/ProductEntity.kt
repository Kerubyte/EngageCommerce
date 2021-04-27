package com.example.engagecommerce.data.entity

data class ProductEntity(
    val uid: String? = null,
    val name: String? = null,
    val brand: String? = null,
    val price: Long? = null,
    val category: String? = null,
    val description: String? = null,
    val details: List<String>? = null,
    val imageUrl: String? = null,
    val delivery: Boolean = false
)