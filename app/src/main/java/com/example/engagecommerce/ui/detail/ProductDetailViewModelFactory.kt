package com.example.engagecommerce.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser

class ProductDetailViewModelFactory(private val productUid: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(productUid) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}