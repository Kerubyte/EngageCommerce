package com.example.engagecommerce.presentation.ui.transaction.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.engagecommerce.application.util.PriceFormatter
import com.example.engagecommerce.data.database.UserRepository
import com.example.engagecommerce.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    private val priceFormatter: PriceFormatter
) : ViewModel() {

    private val _cartValue = MutableLiveData<String>()
    val cartValue: LiveData<String>
        get() = _cartValue

    private val _cartQuantity = MutableLiveData<String>()
    val cartQuantity: LiveData<String>
        get() = _cartQuantity

    private val _isCartNotEmpty = MutableLiveData<Boolean>()
    val isCartNotEmpty: LiveData<Boolean>
        get() = _isCartNotEmpty

    private val user = userRepository.currentUser

    val userCart = user.map { it.cart }

    private fun subscribeObserver() {
        userRepository.userCartState.observeForever {
            when (it) {
                is UserRepository.UserCart.Empty -> handleEmptyCart()
                is UserRepository.UserCart.NotEmpty -> handlePopulatedCart(it.list)
                is UserRepository.UserCart.Error -> handleCartError()
            }
        }
    }

    private fun handleEmptyCart() {
        _cartValue.value = priceFormatter.formatPrice(0)
        _cartQuantity.value = userCart.value?.size.toString()
        _isCartNotEmpty.value = false
    }

    private fun handlePopulatedCart(list: List<Product>) {

        val cartValue = list.map { it.price }.sum()

        _cartValue.value = priceFormatter.formatPrice(cartValue)
        _cartQuantity.value = userCart.value?.size.toString()
        _isCartNotEmpty.value = true
    }

    private fun handleCartError() {
        Log.d("placeholder", "cartError")
    }

    fun handleRemoveFromCart(product: Product) {
        removeFromCart(product)
        getUserData()
        getUserCart()
    }

    private fun getUserData() {
        userRepository.getUserData()
    }

    private fun getUserCart() {
        userRepository.getUserCart()
    }

    private fun removeFromCart(product: Product) {
        userRepository.removeFromCart(product)
    }

    init {
        getUserData()
        getUserCart()
        subscribeObserver()
    }
}
