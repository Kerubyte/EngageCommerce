package com.kerubyte.engagecommerce.application.utils

import android.icu.text.NumberFormat
import javax.inject.Inject

class PriceFormatter
@Inject
constructor() {

    fun formatPrice(price: Double?): String = NumberFormat.getCurrencyInstance().format(price)

}