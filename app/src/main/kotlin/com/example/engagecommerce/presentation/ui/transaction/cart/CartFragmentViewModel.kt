package com.example.engagecommerce.presentation.ui.transaction.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.engagecommerce.data.database.ProductRepository
import com.example.engagecommerce.data.database.UserRepository
import com.example.engagecommerce.domain.model.Product
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel
    @Inject
    constructor(
        private val repository: ProductRepository,
        private val userRepository: UserRepository,
        private val userCom: UserCom
    ) : ViewModel() {

    private val user = userRepository.getUserData()

    val userCart = user?.switchMap {
        repository.getProductsFromCart(it.cart)
    }

    fun calculateCartValue(list: List<Product>): Long {
        var cartValue = 0L

        if (list.isNotEmpty()) {
            for (product in list) {
                cartValue += product.price
            }
        }
        return cartValue
    }

    fun removeFromCart(product: Product) {
        userRepository.removeFromCart(product)
    }

    fun sendProductEvent(list: List<Product>, eventType: ProductEventType) {

        val myParams: HashMap<String, Any> = HashMap()

        for (product in list) {

            myParams["name"] = product.name
            myParams["price"] = product.price.toString()
            myParams["Image_URL"] = product.imageUrl

            userCom.sendProductEvent(
                product.uid,
                eventType,
                myParams
            )
        }
    }
}
