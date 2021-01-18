package com.example.engagecommerce.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.engagecommerce.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthentication {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var cloud: FirebaseCloud = FirebaseCloud()

    // Live Data for navigation
    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate


    // Create User Account in Firebase Auth and add new User to Cloud Storage
    fun createAccount(email: String, password: String, firstName: String, lastName: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener() {
                val user = User(
                    auth.currentUser!!.uid,
                    firstName = firstName,
                    lastName = lastName,
                    auth.currentUser!!.email,
                    listOf()
                )
                cloud.createNewUser(user)

                _navigate.value = true
            }
            .addOnFailureListener { exc ->
                Log.d("register", exc.toString())

            }
    }

    // Log In User
    fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _navigate.value = true
                }
            }
            .addOnFailureListener { exc ->
                Log.d("login", exc.toString())
            }
    }

    // Sign out user
    fun signOut() {
        Firebase.auth.signOut()
        _navigate.value = true
    }

    // Finish Navigation
    fun onDoneNavigating() {
        _navigate.value = false
    }

    init {
        _navigate.value = false
    }
}
