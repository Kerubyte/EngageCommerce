package com.kerubyte.engagecommerce.infrastructure.auth

import com.google.common.truth.Truth
import com.kerubyte.engagecommerce.feature.auth.data.util.InputValidator
import com.kerubyte.engagecommerce.infrastructure.util.*
import org.junit.Test

class InputValidatorTest {

    @Test
    fun `empty email returns false`() {
        val result = InputValidator.isValidEmail(
            ""
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly formatted email returns false`() {
        val result = InputValidator.isValidEmail(
            FAKE_USER_EMAIL_INVALID
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `correctly formatted email returns true`() {
        val result = InputValidator.isValidEmail(
            FAKE_USER_EMAIL_VALID
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `password less than 6 chars returns false`() {
        val result = InputValidator.isValidPassword(
            FAKE_USER_PASSWORD_INVALID
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `password with at least 6 chars returns true`() {
        val result = InputValidator.isValidPassword(
            FAKE_USER_PASSWORD_VALID
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `firstName with less than 3 chars returns false`() {
        val result = InputValidator.isValidName(
            ""
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `firstName with at least 3 chars returns true`() {
        val result = InputValidator.isValidName(
            FAKE_FIRST_NAME_VALID
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `lastName with less than 3 chars returns false`() {
        val result = InputValidator.isValidName(
            ""
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `lastName with at least 3 chars returns true`() {
        val result = InputValidator.isValidName(
            FAKE_LAST_NAME_VALID
        )
        Truth.assertThat(result).isTrue()
    }
}