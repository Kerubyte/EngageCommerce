package com.example.engagecommerce.presentation.ui.register

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.database.ProductRepository
import com.example.engagecommerce.data.database.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        val auth: UserRepository,
        private val repository: ProductRepository

    ): ViewModel() {


    val user = auth.getUserData()

}
