package com.example.engagecommerce.ui.register

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseAuthentication
import com.example.engagecommerce.repo.FirebaseCloud

class RegisterViewModel : ViewModel() {

    val auth  = FirebaseAuthentication()
    val repo = FirebaseCloud()

    val user = repo.getUserData()

}