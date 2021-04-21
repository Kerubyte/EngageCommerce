package com.example.engagecommerce.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.engagecommerce.data.Order
import com.example.engagecommerce.data.Product
import com.example.engagecommerce.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseCloud
@Inject
constructor(
    private val firestore: FirebaseFirestore
) {

    private val auth = FirebaseAuth.getInstance()
    //private val firestore = FirebaseFirestore.getInstance()

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    private val _currentProduct = MutableLiveData<Product>()
    val currentProduct: LiveData<Product>
        get() = _currentProduct

    fun getCurrentUser(): DocumentReference? {
        if (auth.currentUser != null) {
            return firestore.collection("users")
                .document(auth.currentUser!!.uid)
        }
        return null
    }

    fun getUserData(): LiveData<User>? {

        val firestoreResult = MutableLiveData<User>()

        if (auth.currentUser != null) {
            val uid = auth.currentUser?.uid

            firestore.collection("users")
                .document(uid!!)
                .get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    firestoreResult.postValue(user!!)
                    _currentUser.postValue(user)
                }
                .addOnFailureListener {
                    Log.d("repo", it.message.toString())
                }
            return firestoreResult
        }
        return null
    }

    fun createNewUser(user: User) {
        firestore.collection("users")
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

        val firestoreResult = MutableLiveData<List<Product>>()

        firestore.collection("products")
            .get()
            .addOnSuccessListener {
                val products = it.toObjects(Product::class.java)
                firestoreResult.postValue(products)
            }
            .addOnFailureListener {
                Log.d("getProducts", it.message.toString())
            }
        return firestoreResult
    }

    fun getSingleProduct(uid: String): LiveData<Product> {

        val firestoreResult = MutableLiveData<Product>()

        firestore.collection("products")
            .document(uid)
            .get()
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)
                firestoreResult.postValue(product!!)
                _currentProduct.postValue(product)
            }
            .addOnFailureListener {
                Log.d("getSingleProduct", it.message.toString())
            }
        return firestoreResult

    }

    fun addToCart(productUid: String) {

        if (auth.currentUser != null) {
            firestore.collection("users")
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
        firestore.collection("users")
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

        val firestoreResult = MutableLiveData<List<Product>>()

        if (!list.isNullOrEmpty()) {
            firestore.collection("products")
                .whereIn("uid", list)
                .get()
                .addOnSuccessListener {
                    val cartList = it.toObjects(Product::class.java)
                    firestoreResult.postValue(cartList)
                }
                .addOnFailureListener {
                    Log.d("getCart", it.message.toString())
                }
        }
        return firestoreResult
    }

    fun clearUserCart() {

        firestore.collection("users")
            .document(auth.currentUser!!.uid)
            .update(
                mapOf(
                    "cart" to FieldValue.delete()
                )
            )
    }

    fun createNewOrder(list: List<String>, value: String, timeNow: String) {

        val order = hashMapOf<String, Any>()
        order["products"] = list
        order["time"] = timeNow
        order["value"] = value

        firestore.collection("orders")
            .document(auth.currentUser?.uid!!)
            .collection("orders")
            .document(timeNow)
            .set(order)
    }

    fun getOrders(): LiveData<List<Order>> {

        val firestoreResult = MutableLiveData<List<Order>>()

        firestore.collection("orders")
            .document(auth.currentUser!!.uid)
            .collection("orders")
            .get()
            .addOnSuccessListener {
                val order = it.toObjects(Order::class.java)
                firestoreResult.postValue(order)
            }
            .addOnFailureListener {
                Log.d("getOrders", it.message.toString())
            }
        return firestoreResult
    }

    fun getProductsFromOrder(list: List<String>?): LiveData<List<Product>> {

        val firestoreResult = MutableLiveData<List<Product>>()

        if (!list.isNullOrEmpty()) {
            firestore.collection("orders")
                .document(auth.currentUser!!.uid)
                .collection("orders")
                .whereIn("uid", list)
                .get()
                .addOnSuccessListener {
                    val cartList = it.toObjects(Product::class.java)
                    firestoreResult.postValue(cartList)
                }
                .addOnFailureListener {
                    Log.d("getProductsFromOrder", it.message.toString())
                }
        }
        return firestoreResult
    }
}