package com.example.engagecommerce.presentation.ui.login

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.application.util.Utils
import com.example.engagecommerce.data.database.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val auth: UserRepository
) : ViewModel() {

    val navigate = auth.navigate

    fun onDoneNavigating() {
        auth.onDoneNavigating()
    }

    fun loginUser(email: String, password: String) {
        if (!Utils.validateEmailAndPassword(email, password)) {
            val errorMessage = "Invalid Email or Password"

            return
        }
        auth.loginUser(email, password)
    }

}