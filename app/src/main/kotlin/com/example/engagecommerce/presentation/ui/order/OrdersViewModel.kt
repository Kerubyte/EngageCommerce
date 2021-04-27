package com.example.engagecommerce.presentation.ui.order

import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.database.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel
@Inject
constructor(
    orderRepository: OrderRepository
) : ViewModel() {

    val orders = orderRepository.getOrders()
}