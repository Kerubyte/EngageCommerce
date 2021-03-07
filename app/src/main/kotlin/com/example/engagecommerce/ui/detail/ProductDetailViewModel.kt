package com.example.engagecommerce.ui.detail

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.repo.FirebaseCloud
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType

class ProductDetailViewModel(productUid: String) : ViewModel() {

    private val repository = FirebaseCloud()

    val product = repository.getSingleProduct(productUid)
    val currentUser = repository.getCurrentUser()

    fun addToCart(productUid: String) {
        repository.addToCart(productUid)
    }

    fun sendProductEvent(product: Product, eventType: ProductEventType) {

        val myParams: HashMap<String, Any> = HashMap()

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