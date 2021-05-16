package com.example.engagecommerce.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.database.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        val userRepository: UserRepository
    ): ViewModel() {

    val navigate: LiveData<Boolean>
        get() = userRepository.navigate

    fun onDoneNavigating() {
        userRepository.onDoneNavigating()
    }

    val user = userRepository.getUserData()

    fun createUser(email: String, password: String, firstName: String, lastName: String) {
        userRepository.createAccount(email, password, firstName, lastName)
    }

}
