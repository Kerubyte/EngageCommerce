package com.kerubyte.engagecommerce.infrastructure.util

import android.icu.text.NumberFormat

class PriceFormatter {

    fun formatPrice(price: Double?): String = NumberFormat.getCurrencyInstance().format(price)

}