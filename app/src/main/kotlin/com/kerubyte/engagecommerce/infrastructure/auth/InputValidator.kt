package com.kerubyte.engagecommerce.infrastructure.auth

import java.util.regex.Pattern

object InputValidator {

    fun isValidEmail(email: String): Boolean {
        return Pattern.matches(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+",
            email
        )
    }

    fun isValidPassword(password: String) = password.length >= 6

    fun isValidName(name: String) = name.length >= 3
}