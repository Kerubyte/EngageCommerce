package com.example.engagecommerce.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.engagecommerce.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.user.sdk.UserCom
import com.user.sdk.customer.Customer
import com.user.sdk.customer.CustomerUpdateCallback
import com.user.sdk.customer.RegisterResponse

class FirebaseAuthentication {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var cloud: FirebaseCloud = FirebaseCloud()

    private val objectCustomerCallback = object : CustomerUpdateCallback {
        override fun onSuccess(p0: RegisterResponse) {
            Log.d("register", "UserCom Registered")
        }

        override fun onFailure(p0: Throwable) {
            Log.d("register", "UserCom Failure")
        }
    }

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    fun createAccount(email: String, password: String, firstName: String, lastName: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = User(
                    auth.currentUser!!.uid,
                    firstName = firstName,
                    lastName = lastName,
                    auth.currentUser!!.email,
                    listOf()
                )
                val customer = Customer()
                    .id(auth.currentUser!!.uid)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)

                cloud.createNewUser(user)
                UserCom.getInstance()
                    .register(customer, objectCustomerCallback)

                _navigate.value = true
            }
            .addOnFailureListener { exc ->
                Log.d("register", exc.toString())
            }
    }

    fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val customer = Customer()
                    .id(auth.currentUser!!.uid)
                    .email(email)

                UserCom.getInstance()
                    .register(customer, objectCustomerCallback)

                _navigate.value = true
            }
            .addOnFailureListener { exc ->
                Log.d("login", exc.toString())
            }
    }

    fun signOut() {
        Firebase.auth.signOut()
        UserCom.getInstance().logout()
        _navigate.value = true
    }

    fun onDoneNavigating() {
        _navigate.value = false
    }

    init {
        _navigate.value = false
    }
}
