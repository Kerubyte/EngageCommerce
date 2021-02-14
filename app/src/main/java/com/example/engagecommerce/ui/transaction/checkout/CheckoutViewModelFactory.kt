package com.example.engagecommerce.ui.transaction.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CheckoutViewModelFactory(private val cartValue: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(cartValue) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}