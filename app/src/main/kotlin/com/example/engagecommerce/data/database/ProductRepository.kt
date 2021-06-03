package com.example.engagecommerce.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.engagecommerce.application.repo.FirebaseDatabase
import com.example.engagecommerce.data.database.mappers.NullableInputProductEntityMapper
import com.example.engagecommerce.data.entity.ProductEntity
import com.example.engagecommerce.domain.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ProductRepository
@Inject constructor(
    private val firestore: FirebaseFirestore,
    private val inputProductMapper: NullableInputProductEntityMapper
) : FirebaseDatabase {

    val currentProduct = MutableLiveData<Product>()

    override fun getProducts(): LiveData<List<Product>> {

        val responseResult = MutableLiveData<List<Product>>()

        firestore.collection("products")
            .get()
            .addOnSuccessListener {
                val productEntities = it.toObjects(ProductEntity::class.java)
                val products = inputProductMapper.mapFromEntityList(productEntities)
                responseResult.value = products
            }
            .addOnFailureListener { e ->
                Log.d("getProducts", e.toString())
            }
        return responseResult
    }

    override fun getSingleProduct(uid: String): LiveData<Product> {

        val responseResult = MutableLiveData<Product>()

        firestore.collection("products")
            .document(uid)
            .get()
            .addOnSuccessListener {
                val productEntity = it.toObject(ProductEntity::class.java)
                val product = productEntity?.let { entity ->
                    inputProductMapper.mapFromEntity(entity)
                }
                responseResult.value = product!!
                currentProduct.value = product!!
            }
            .addOnFailureListener { e ->
                Log.d("getSingleProduct", e.toString())
            }
        return responseResult
    }

    override fun getProductsFromCart(list: List<Product>?): LiveData<List<Product>> {

        val firestoreResult = MutableLiveData<List<Product>>()

        if (!list.isNullOrEmpty()) {
            firestore.collection("products")
                .whereIn("uid", list)
                .get()
                .addOnSuccessListener {
                    val cartEntityList = it.toObjects(ProductEntity::class.java)
                    val cartList = inputProductMapper.mapFromEntityList(cartEntityList)
                    firestoreResult.value = cartList
                }
                .addOnFailureListener {
                    Log.d("getCart", it.toString())
                }
        }
        return firestoreResult
    }
}