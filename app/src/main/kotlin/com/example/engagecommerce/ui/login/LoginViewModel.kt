package com.example.engagecommerce.ui.login

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseAuthentication

class LoginViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuthentication()
    val navigate = firebaseAuth.navigate

    fun onDoneNavigating() {
        firebaseAuth.onDoneNavigating()
    }

    fun loginUser(email: String, password: String) {
        firebaseAuth.loginUser(email, password)
    }

}