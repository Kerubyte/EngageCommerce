package com.example.engagecommerce.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.User
import com.example.engagecommerce.repo.FirebaseCloud
import com.google.firebase.firestore.ktx.toObject
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType

class ProductDetailViewModel(private val productUid: String) : ViewModel() {

    private val repository = FirebaseCloud()
    val product = repository.getSingleProduct(productUid)
    private val currentUser = repository.getCurrentUser()

    private val _productInCart = MutableLiveData<Boolean>()
    val productInCart: LiveData<Boolean>
            get() = _productInCart

    val snapshotListenerRegistration = currentUser?.addSnapshotListener { querySnapshot, error ->
        error?.let {
            Log.d("snapshotProduct", it.message.toString())
            return@addSnapshotListener
        }
        querySnapshot?.let {
            val user = it.toObject<User>()
            _productInCart.value = checkForProductInCart(user?.cart)
        }
    }

    private fun checkForProductInCart(userCart: List<String>?): Boolean {
        return if (userCart != null) productUid !in userCart
        else true
    }

    fun addToCart() {
        repository.addToCart(productUid)
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