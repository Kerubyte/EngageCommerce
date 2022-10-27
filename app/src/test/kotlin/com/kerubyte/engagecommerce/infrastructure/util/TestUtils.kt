package com.kerubyte.engagecommerce.infrastructure.util

import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import com.kerubyte.engagecommerce.common.domain.model.UserModel

val testProductModels = mutableListOf(
    ProductModel("1", "firstProduct", "Machine", 5.00, "$ 5.00"),
    ProductModel("2", "secondProduct", "Machine", 6.00, "$ 6.00"),
    ProductModel("3", "thirdProduct", "Machine", 7.00, "$ 7.00"),
    ProductModel("4", "fourthProduct", "Machine", 8.00, "$ 8.00")
)

val testOrders = mutableListOf(
    mapOf("products" to "1", "value" to FAKE_CART_SAVED_STATE_VALUE)
)

val currentUserTest: UserModel = UserModel(
    "1234",
    "Test",
    "Testing",
    "test@example.com",
    mutableListOf("1", "2"),
    mapOf()
)