package com.kerubyte.engagecommerce.infrastructure.state

sealed class CartState {

    object Empty: CartState()
    data class NotEmpty(val list: List<String>): CartState()
    object Error: CartState()
}
