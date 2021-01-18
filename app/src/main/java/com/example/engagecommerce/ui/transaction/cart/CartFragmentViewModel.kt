package com.example.engagecommerce.ui.transaction.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.repo.FirebaseCloud


class CartFragmentViewModel : ViewModel() {

    private val repository = FirebaseCloud()
    private val user = repository.getUserData()


    val userCart = user?.switchMap {
        repository.getProductsFromCart(it.cart)
    }

    fun calculateCartValue(list: List<Product>): Long {

        var cartValue = 0L

        if (list.isNotEmpty()) {
            for (product in list) {
                cartValue += product.price!!
            }
        }

        return cartValue
    }

    fun removeFromCart(product: Product) {
        repository.removeFromCart(product)
    }
}
