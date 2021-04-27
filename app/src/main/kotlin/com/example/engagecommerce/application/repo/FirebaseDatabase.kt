package com.example.engagecommerce.application.repo

import androidx.lifecycle.LiveData
import com.example.engagecommerce.domain.model.Product

interface FirebaseDatabase {

    fun getProducts(): LiveData<List<Product>>

    fun getSingleProduct(uid: String): LiveData<Product>

    fun getProductsFromCart(list: List<String>?): LiveData<List<Product>>

}