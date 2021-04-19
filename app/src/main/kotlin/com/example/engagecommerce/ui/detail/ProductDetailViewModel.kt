package com.example.engagecommerce.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.engagecommerce.data.ProductEntity
import com.example.engagecommerce.data.User
import com.example.engagecommerce.repo.FirebaseCloud
import com.example.engagecommerce.utils.Utils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType
import java.util.*

class ProductDetailViewModel(private val productUid: String) : ViewModel() {

    private val auth = Firebase.auth
    private val repository = FirebaseCloud()
    private val currentUser = repository.getCurrentUser()

    private val _isProductInCart = MutableLiveData<Boolean>()
    val isProductInCart: LiveData<Boolean>
        get() = _isProductInCart

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    val currentProduct: LiveData<ProductEntity>
        get() = repository.currentProduct.map {
            ProductEntity(
                it.uid,
                it.name,
                it.brand,
                Utils.formatPrice.format(it.price),
                it.imageUrl,
                it.delivery,
                it.category,
                it.description
            )
        }

    private val snapshotListenerRegistration =
        currentUser?.addSnapshotListener { querySnapshot, error ->
            error?.let {
                Log.d("snapshotProduct", it.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val user = it.toObject<User>()
                _isProductInCart.value = checkForProductInCart(user?.cart)
            }
        }

    fun handleAddToCartClick() {
        if (auth.currentUser == null) {
            navigateToLogin()
            onDoneNavigating()
        } else {
            addToCart()
        }
    }

    private fun checkForProductInCart(userCart: List<String>?): Boolean {
        return if (userCart != null) productUid !in userCart
        else true
    }

    private fun addToCart() {
        repository.addToCart(productUid)
        sendProductEvent(ProductEventType.ADD_TO_CART)
    }

    private fun sendProductEvent(eventType: ProductEventType) {

        val productAttrs = mapOf<String, Any?>(
            "name" to currentProduct.value?.name,
            "price" to currentProduct.value?.price,
            "Image_URL" to currentProduct.value?.imageUrl
        )

        UserCom.getInstance().sendProductEvent(
            productUid,
            eventType,
            productAttrs
        )
    }

    private fun getSingleProduct() {
        repository.getSingleProduct(productUid)
    }

    private fun navigateToLogin() {
        _navigate.value = true
    }

    private fun onDoneNavigating() {
        _navigate.value = false
    }

    private fun isCurrentUser() {
        if (currentUser == null) _isProductInCart.value = true
    }

    override fun onCleared() {
        super.onCleared()
        snapshotListenerRegistration?.remove()
    }

    init {
        getSingleProduct()
        onDoneNavigating()
        isCurrentUser()
        sendProductEvent(ProductEventType.DETAIL)
    }
}