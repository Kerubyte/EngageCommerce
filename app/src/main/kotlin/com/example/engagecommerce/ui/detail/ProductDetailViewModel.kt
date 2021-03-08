package com.example.engagecommerce.ui.detail

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseCloud
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType

class ProductDetailViewModel(private val productUid: String) : ViewModel() {

    private val repository = FirebaseCloud()

    val product = repository.getSingleProduct(productUid)
    val currentUser = repository.getCurrentUser()

    fun addToCart() {
        repository.addToCart(productUid)
    }

    fun checkForProductInCart(userCart: List<String>?): Boolean {
        return if (userCart != null) productUid !in userCart
        else false
    }

    fun sendProductEvent(eventType: ProductEventType) {

        val myParams: HashMap<String, Any> = HashMap()

        myParams["name"] = product.value?.name.toString()
        myParams["price"] = product.value?.price.toString()
        myParams["Image_URL"] = product.value?.imageUrl.toString()

        UserCom.getInstance().sendProductEvent(
            productUid,
            eventType,
            myParams
        )
    }
}