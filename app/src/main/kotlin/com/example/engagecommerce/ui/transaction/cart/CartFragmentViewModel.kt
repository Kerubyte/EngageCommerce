package com.example.engagecommerce.ui.transaction.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.repo.FirebaseCloud
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType


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

    fun sendProductEvent(list: List<Product>, eventType: ProductEventType) {

        val myParams: HashMap<String, Any> = HashMap()

        for (product in list) {

            myParams["name"] = product.name.toString()
            myParams["price"] = product.price.toString()
            myParams["Image_URL"] = product.imageUrl.toString()

            UserCom.getInstance().sendProductEvent(
                product.uid.toString(),
                eventType,
                myParams
            )
        }
    }
}
