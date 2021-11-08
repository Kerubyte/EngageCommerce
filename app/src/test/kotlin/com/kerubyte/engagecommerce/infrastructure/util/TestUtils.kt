package com.kerubyte.engagecommerce.infrastructure.util

import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.domain.model.User

val testProducts = mutableListOf(
    Product("1", "firstProduct", "Machine", 5.00, "$ 5.00"),
    Product("2", "secondProduct", "Machine", 6.00, "$ 6.00"),
    Product("3", "thirdProduct", "Machine", 7.00, "$ 7.00"),
    Product("4", "fourthProduct", "Machine", 8.00, "$ 8.00")
)

val testOrders = mutableListOf(
    mapOf("products" to "1", "value" to FAKE_CART_SAVED_STATE_VALUE)
)

val currentUserTest: User = User(
    "1234",
    "Test",
    "Testing",
    "test@example.com",
    mutableListOf("1", "2"),
    mapOf()
)