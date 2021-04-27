package com.example.engagecommerce.domain.model

data class Order(
    val value: String? = null,
    val time: String? = null,
    val products: List<String>? = null
)