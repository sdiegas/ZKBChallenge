package com.sdiegas.zkbchallenge.domain.usecase

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month

class ValidationBirthdayTest {
    private val birthdayValidator = ValidateBirthday()

    @Test
    fun validateBirthdayMinFail() {
        val testBirthday = LocalDateTime.of(1899, Month.DECEMBER, 31, 23, 59)

        val result = birthdayValidator.execute(testBirthday)

        Assert.assertFalse(result.successful)
    }

    @Test
    fun validateBirthdayMaxFail() {
        val testBirthday = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 1)

        val result = birthdayValidator.execute(testBirthday)

        Assert.assertFalse(result.successful)
    }

    @Test
    fun validateBirthdaySuccess() {
        val testBirthday = LocalDateTime.of(1993, Month.JUNE, 6, 9, 30)

        val result = birthdayValidator.execute(testBirthday)

        Assert.assertTrue(result.successful)
    }
}