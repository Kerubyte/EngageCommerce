package com.example.engagecommerce.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.User
import com.example.engagecommerce.repo.FirebaseAuthentication
import com.example.engagecommerce.repo.FirebaseCloud

class ProfileViewModel : ViewModel() {

    private val repository = FirebaseCloud()
    private val auth = FirebaseAuthentication()

    val navigate: LiveData<Boolean>
        get() = auth.navigate

    val currentUser: LiveData<User>
        get() = repository.currentUser

    fun onDoneNavigating() {
        auth.onDoneNavigating()
    }

    fun signOut() {
        auth.signOut()
    }

    init {
        repository.getUserData()
    }
}