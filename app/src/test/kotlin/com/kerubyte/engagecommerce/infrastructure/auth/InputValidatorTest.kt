package com.kerubyte.engagecommerce.infrastructure.auth

import com.google.common.truth.Truth
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
            "hellogoogle@"
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `correctly formatted email returns true`() {
        val result = InputValidator.isValidEmail(
            "dawid@user.com"
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `password less than 6 chars returns false`() {
        val result = InputValidator.isValidPassword(
            ""
        )
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `password with at least 6 chars returns true`() {
        val result = InputValidator.isValidPassword(
            "123456"
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `firstName with at least 3 chars returns true`() {
        val result = InputValidator.isValidFirstName(
            "abc"
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `lastName with at least 3 chars returns true`() {
        val result = InputValidator.isValidLastName(
            "abc"
        )
        Truth.assertThat(result).isTrue()
    }
}