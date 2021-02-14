package com.example.engagecommerce.ui.activity

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseCloud

class MainViewModel : ViewModel() {

    val repo = FirebaseCloud()
    val user = repo.getUserData()
    val currentUser = repo.getCurrentUser()
}