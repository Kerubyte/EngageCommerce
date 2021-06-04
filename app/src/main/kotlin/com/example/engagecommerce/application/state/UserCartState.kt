package com.example.engagecommerce.application.state

import com.example.engagecommerce.domain.model.Product

class UserCartState {

    sealed class UserCart {

        object Empty : UserCart()
        data class NotEmpty(val list: List<Product>) : UserCart()
        object Error : UserCart()
    }
}