package com.example.engagecommerce.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.engagecommerce.application.repo.OrderDatabase
import com.example.engagecommerce.data.entity.ProductEntity
import com.example.engagecommerce.domain.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class OrderRepository
@Inject
constructor(
    private val repository: FirebaseFirestore,
    private val auth: FirebaseAuth
) : OrderDatabase {

    override fun createNewOrder(list: List<String>, value: String, timeNow: String) {
        val order = hashMapOf<String, Any>()
        order["products"] = list
        order["time"] = timeNow
        order["value"] = value

        repository.collection("orders")
            .document(auth.currentUser?.uid!!)
            .collection("orders")
            .document(timeNow)
            .set(order)
    }

    override fun getOrders(): LiveData<List<Order>> {
        val result = MutableLiveData<List<Order>>()

        repository.collection("orders")
            .document(auth.currentUser!!.uid)
            .collection("orders")
            .get()
            .addOnSuccessListener {
                val order = it.toObjects(Order::class.java)
                result.postValue(order)
            }
            .addOnFailureListener {
                Log.d("getOrders", it.message.toString())
            }
        return result
    }

    override fun getProductsFromOrder(list: List<String>?): LiveData<List<ProductEntity>> {
        val result = MutableLiveData<List<ProductEntity>>()

        if (!list.isNullOrEmpty()) {
            repository.collection("orders")
                .document(auth.currentUser!!.uid)
                .collection("orders")
                .whereIn("uid", list)
                .get()
                .addOnSuccessListener {
                    val cartList = it.toObjects(ProductEntity::class.java)
                    result.postValue(cartList)
                }
                .addOnFailureListener {
                    Log.d("getProductsFromOrder", it.message.toString())
                }
        }
        return result
    }
}
