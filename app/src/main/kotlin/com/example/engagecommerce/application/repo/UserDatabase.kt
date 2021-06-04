package com.example.engagecommerce.application.repo

import androidx.lifecycle.LiveData
import com.example.engagecommerce.domain.model.Product
import com.example.engagecommerce.domain.model.User
import com.google.firebase.firestore.DocumentReference

interface UserDatabase {

    fun getCurrentUser(): DocumentReference?

    fun getUserData(): LiveData<User>?

    fun getUserCart()

    fun addToCart(product: Product)

    fun removeFromCart(product: Product)

    fun clearUserCart()

    fun createNewUser(user: User)

    fun createAccount(email: String, password: String, firstName: String, lastName: String)

    fun loginUser(email: String, password: String)

    fun signOut()

    fun onDoneNavigating()
}