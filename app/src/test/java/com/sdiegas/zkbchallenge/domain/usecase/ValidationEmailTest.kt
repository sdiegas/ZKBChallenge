package com.sdiegas.zkbchallenge.domain.usecase

import org.junit.Assert
import org.junit.Test

class ValidationEmailTest {
    private val emailValidator = ValidateEmail()

    @Test
    fun validateEmailFail() {
        val testEmail = "s"

        val result = emailValidator.execute(testEmail)

        Assert.assertFalse(result.successful)
    }

    @Test
    fun validateEmailFail2() {
        val testEmail = "stefandiegas@"

        val result = emailValidator.execute(testEmail)

        Assert.assertFalse(result.successful)
    }

    @Test
    fun validateEmailFail3() {
        val testEmail = "stefandiegas@gmail"

        val result = emailValidator.execute(testEmail)

        Assert.assertFalse(result.successful)
    }

    @Test
    fun validateEmailSuccess1() {
        val testEmail = "stefandiegas@gmail.com"

        val result = emailValidator.execute(testEmail)

        Assert.assertTrue(result.successful)
    }

    @Test
    fun validateEmailSuccess2() {
        val testEmail = "a@b.ch"

        val result = emailValidator.execute(testEmail)

        Assert.assertTrue(result.successful)
    }

}