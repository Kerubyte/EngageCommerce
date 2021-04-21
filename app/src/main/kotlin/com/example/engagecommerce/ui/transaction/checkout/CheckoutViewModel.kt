package com.example.engagecommerce.ui.transaction.checkout

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseCloud

class CheckoutViewModel(private val cartValue: String) : ViewModel() {

    private val repository = FirebaseCloud()
    val user = repository.getUserData()

    private fun clearUserCart() {
        repository.clearUserCart()
    }

    private fun createOrderFromCart() {
        val timeNow = Calendar.getInstance().time.toString()
        user?.value?.cart?.let { repository.createNewOrder(it, cartValue, timeNow) }
    }

    fun placeOrder() {
        createOrderFromCart()
        clearUserCart()

    }
}