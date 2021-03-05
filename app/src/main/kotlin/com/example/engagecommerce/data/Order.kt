package com.example.engagecommerce.data

data class Order(
    val value: String? = null,
    val time: String? = null,
    val products: List<String>? = null
)