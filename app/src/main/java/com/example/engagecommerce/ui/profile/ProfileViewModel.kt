package com.example.engagecommerce.ui.profile

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseCloud

class ProfileViewModel : ViewModel() {

    private val repository = FirebaseCloud()

    val user = repository.getUserData()
}