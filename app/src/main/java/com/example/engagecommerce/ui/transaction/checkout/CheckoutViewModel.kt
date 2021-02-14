package com.example.engagecommerce.ui.transaction.checkout

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseCloud

class CheckoutViewModel(cartValue: String) : ViewModel() {

    private val repository = FirebaseCloud()

    val user = repository.getUserData()
}