package com.example.engagecommerce.ui.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.data.User
import com.example.engagecommerce.repo.FirebaseCloud
import com.google.firebase.auth.FirebaseAuth

class TitleScreenViewModel: ViewModel() {

    private val repository = FirebaseCloud()
    val products = repository.getProducts()

    fun getSingleProduct(product: Product): String {
        val productUid = repository.getSingleProduct(product.uid.toString())
        return productUid.value.toString()
    }


}