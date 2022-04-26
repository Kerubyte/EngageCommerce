package com.kerubyte.engagecommerce.infrastructure.util


import android.icu.text.NumberFormat
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import org.junit.Before
import org.junit.Test

class PriceFormatterTest {

    private val priceFormatter = PriceFormatter()

    @MockK
    lateinit var num: NumberFormat


    @Before
    fun setUp() {

        num = NumberFormat.getCurrencyInstance()


    }

    @Test
    fun nullValuePassed_returnsCorrectFormat() {

        val result = priceFormatter.formatPrice(null)

        assertThat(result).isEqualTo("$0.00")

    }
}