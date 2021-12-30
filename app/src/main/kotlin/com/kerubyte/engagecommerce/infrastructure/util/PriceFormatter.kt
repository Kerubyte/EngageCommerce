package com.kerubyte.engagecommerce.infrastructure.util

import android.icu.text.NumberFormat
import com.kerubyte.engagecommerce.infrastructure.Constants.DEFAULT_CART_VALUE
import java.util.*

class PriceFormatter {

    private val locale = Locale.UK
    private val localisedFormatter = NumberFormat.getCurrencyInstance(locale)

    fun formatPrice(price: Double?): String =

        when (price) {

            is Double -> localisedFormatter.format(price)
            else -> localisedFormatter.format(DEFAULT_CART_VALUE)
        }
}