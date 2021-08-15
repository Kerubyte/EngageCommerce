package com.kerubyte.engagecommerce.application.utils

import android.icu.text.NumberFormat

class PriceFormatter {

    fun formatPrice(price: Double?): String = NumberFormat.getCurrencyInstance().format(price)

}