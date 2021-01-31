package com.example.engagecommerce.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseCloud {

    private val auth = FirebaseAuth.getInstance()
    private val cloud = FirebaseFirestore.getInstance()

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser


    fun getCurrentUser(): DocumentReference? {
        if (auth.currentUser != null) {
            return cloud.collection("users")
                .document(auth.currentUser!!.uid)
        }
        return null
    }


        fun getUserData(): LiveData<User>? {

            val cloudResult = MutableLiveData<User>()
            if (auth.currentUser != null) {
                val uid = auth.currentUser?.uid

                cloud.collection("users")
                    .document(uid!!)
                    .get()
                    .addOnSuccessListener {
                        if (auth.currentUser != null) {
                            val user = it.toObject(User::class.java)
                            cloudResult.postValue(user)
                        }
                    }
                    .addOnFailureListener {
                        Log.d("repo", it.message.toString())
                    }
                return cloudResult
            }
            return null
        }

        fun createNewUser(user: User) {
            cloud.collection("users")
                .document(user.uid!!)
                .set(user)
                .addOnSuccessListener {
                    val newUser = User(
                        uid = user.uid,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        email = user.email,
                        listOf()
                    )
                    _currentUser.value = newUser
                }
        }

        fun getProducts(): LiveData<List<Product>> {

            val cloudResult = MutableLiveData<List<Product>>()

            cloud.collection("products")
                .get()
                .addOnSuccessListener {
                    val product = it.toObjects(Product::class.java)
                    cloudResult.postValue(product)
                }
                .addOnFailureListener {
                    Log.d("getProducts", it.message.toString())
                }
            return cloudResult
        }

        fun getSingleProduct(uid: String): LiveData<Product> {

            val cloudResult = MutableLiveData<Product>()

            cloud.collection("products")
                .document(uid)
                .get()
                .addOnSuccessListener {
                    val product = it.toObject(Product::class.java)
                    cloudResult.postValue(product)
                }
                .addOnFailureListener {
                    Log.d("getSingleProduct", it.message.toString())
                }
            return cloudResult

        }

        fun addToCart(productUid: String) {

            if (auth.currentUser != null) {
                cloud.collection("users")
                    .document(auth.currentUser?.uid!!)
                    .update("cart", FieldValue.arrayUnion(productUid))
                    .addOnSuccessListener {
                        Log.d("cart", "Added to Cart")
                    }
                    .addOnFailureListener {
                        Log.d("cart", it.message.toString())
                    }
            }
        }

        fun removeFromCart(product: Product) {
            cloud.collection("users")
                .document(auth.currentUser?.uid!!)
                .update("cart", FieldValue.arrayRemove(product.uid))
                .addOnSuccessListener {
                    Log.d("cart", "Removed from Cart")
                }
                .addOnFailureListener {
                    Log.d("cart", it.message.toString())
                }
        }

        fun getProductsFromCart(list: List<String>?): LiveData<List<Product>> {

            val cloudResult = MutableLiveData<List<Product>>()

            if (!list.isNullOrEmpty()) {
                cloud.collection("products")
                    .whereIn("uid", list)
                    .get()
                    .addOnSuccessListener {
                        val cartList = it.toObjects(Product::class.java)
                        cloudResult.postValue(cartList)
                    }
                    .addOnFailureListener {
                        Log.d("getCart", it.message.toString())
                    }
            }
            return cloudResult
        }
    }