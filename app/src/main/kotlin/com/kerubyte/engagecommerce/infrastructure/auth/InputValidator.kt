package com.kerubyte.engagecommerce.infrastructure.auth

import java.util.regex.Pattern

object InputValidator {

    /**
     * the input is not valid if...
     * ...email is not in correct format
     * ...email is empty
     * ...password is less than 6 chars
     * ...firstName is less than 3 chars
     * ...lastName is less than 3 chars
     */

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

    fun isValidFirstName(firstName: String) = firstName.length >= 3

    fun isValidLastName(lastName: String) = lastName.length >= 3
}