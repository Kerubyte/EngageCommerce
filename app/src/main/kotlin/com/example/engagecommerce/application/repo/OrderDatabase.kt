package com.example.engagecommerce.application.repo

import androidx.lifecycle.LiveData
import com.example.engagecommerce.data.entity.ProductEntity
import com.example.engagecommerce.domain.model.Order

interface OrderDatabase {

    fun createNewOrder(list: List<String>, value: String, timeNow: String)

    fun getOrders(): LiveData<List<Order>>

    fun getProductsFromOrder(list: List<String>?): LiveData<List<ProductEntity>>
}