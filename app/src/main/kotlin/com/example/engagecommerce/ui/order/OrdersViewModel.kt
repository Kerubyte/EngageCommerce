package com.example.engagecommerce.ui.order

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.repo.FirebaseCloud

class OrdersViewModel : ViewModel() {

    private val repository = FirebaseCloud()
    val orders = repository.getOrders()


}