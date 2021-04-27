package com.example.engagecommerce.presentation.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.engagecommerce.data.database.ProductRepository
import com.example.engagecommerce.data.database.UserRepository
import com.example.engagecommerce.data.entity.UserEntity
import com.example.engagecommerce.domain.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject
import com.user.sdk.UserCom
import com.user.sdk.events.ProductEventType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val userCom: UserCom
) : ViewModel() {

    private val productUid = savedStateHandle.get<String>("productUid")
    private val currentUser = userRepository.getCurrentUser()

    private val _isProductInCart = MutableLiveData<Boolean>()
    val isProductInCart: LiveData<Boolean>
        get() = _isProductInCart

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    val currentProduct: LiveData<Product>
        get() = repository.currentProduct

    private val snapshotListenerRegistration =
        currentUser?.addSnapshotListener { querySnapshot, error ->
            error?.let {
                Log.d("snapshotProduct", it.toString())
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val user = it.toObject<UserEntity>()
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
        if (productUid != null) {
            userRepository.addToCart(productUid)
        }
       sendProductEvent(ProductEventType.ADD_TO_CART)
    }

    private fun sendProductEvent(eventType: ProductEventType) {

        val productAttrs = mapOf<String, Any?>(
            "name" to currentProduct.value?.name,
            "price" to currentProduct.value?.price,
            "Image_URL" to currentProduct.value?.imageUrl
        )

        if (productUid != null) {
            userCom.sendProductEvent(
                productUid,
                eventType,
                productAttrs
            )
        }
    }

    private fun getSingleProduct() {
        if (productUid != null) {
            repository.getSingleProduct(productUid)
        }
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