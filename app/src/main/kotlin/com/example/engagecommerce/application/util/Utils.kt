package com.example.engagecommerce.application.util

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.*

object Utils {

    private val locale: Locale = Locale.UK
    val formatPrice: NumberFormat = NumberFormat.getCurrencyInstance(locale)

    fun validateEmailAndPassword(email: String, password: String): Boolean {

        var valid = true

        if (email.isNotEmpty()) {
            valid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        if (password.length < 6) {
            valid = false
        }
        return valid
    }

    fun validateFirstAndLastName(firstName: String, lastName: String): Boolean {

        var valid = true

        if (firstName.length < 3) {
            valid = false
        }

        if (lastName.length < 3) {
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