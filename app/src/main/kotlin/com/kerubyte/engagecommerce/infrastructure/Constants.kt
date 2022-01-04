package com.kerubyte.engagecommerce.infrastructure

import com.user.sdk.events.ProductEventType

object Constants {

    const val COLLECTION_USERS = "users"
    const val COLLECTION_PRODUCTS = "products"
    const val COLLECTION_ORDERS = "orders"

    const val DOCUMENT_FIELD_ID = "uid"
    const val DOCUMENT_FIELD_CART = "cart"
    const val DOCUMENT_FIELD_ADDRESS = "address"

    const val DEFAULT_CART_VALUE = 0.00

    val EVENT_DETAIL = ProductEventType.DETAIL
    val EVENT_ADD_TO_CART = ProductEventType.ADD_TO_CART
    val EVENT_CHECKOUT = ProductEventType.CHECKOUT
    val EVENT_PURCHASE = ProductEventType.PURCHASE
    val EVENT_REMOVE = ProductEventType.REMOVE

}