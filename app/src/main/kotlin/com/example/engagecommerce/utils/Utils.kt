package com.example.engagecommerce.utils

import android.icu.util.Calendar
import android.text.TextUtils
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.*

object Utils {

    val locale: Locale = Locale.UK
    val formatPrice: NumberFormat = NumberFormat.getCurrencyInstance(locale)
    val timeNow = Calendar.getInstance().time.toString()

    // Validate if the required inputs are not empty and correctly formatted
    fun validateEmailAndPassword(email: String, password: String): Boolean {
        var valid = true

        if (TextUtils.isEmpty(email)) {
            valid = false
        }

        if (TextUtils.isEmpty(password) && password.length <= 6) {
            valid = false
        }
        return valid
    }

    fun validateFirstAndLastName(firstName: String, lastName: String): Boolean {
        var valid = true

        if (TextUtils.isEmpty(firstName)) {
            valid = false
        }

        if (TextUtils.isEmpty(lastName)) {
            valid = false
        }
        return valid
    }

    fun showSnackbarOnError(message: String, view: View) {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

}