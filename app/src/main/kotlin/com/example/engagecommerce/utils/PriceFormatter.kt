package com.example.engagecommerce.utils

import java.text.NumberFormat
import javax.inject.Inject


class PriceFormatter @Inject constructor() {

    fun formatPrice (price: Long): String {
        return NumberFormat.getCurrencyInstance().format(price)
    }
}

