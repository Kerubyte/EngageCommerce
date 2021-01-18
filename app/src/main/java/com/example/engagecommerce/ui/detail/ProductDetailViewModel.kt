package com.example.engagecommerce.ui.detail

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseAuthentication
import com.example.engagecommerce.repo.FirebaseCloud
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

class ProductDetailViewModel(productUid: String) : ViewModel() {

    private val repository = FirebaseCloud()
    val product = repository.getSingleProduct(productUid)

    val user = repository.getUserData()

    val costam = repository.listen()




}