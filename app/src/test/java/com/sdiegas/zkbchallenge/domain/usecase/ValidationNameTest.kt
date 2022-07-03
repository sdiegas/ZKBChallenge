package com.sdiegas.zkbchallenge.domain.usecase

import org.junit.Assert
import org.junit.Test

class ValidationNameTest {
    private val nameValidator = ValidateName()

    @Test
    fun validateNameFail() {
        val testName = ""

        val result = nameValidator.execute(testName)

        Assert.assertFalse(result.successful)
    }

    @Test
    fun validateNameSuccess() {
        val testName = "Stefan Diegas"

        val result = nameValidator.execute(testName)

        Assert.assertTrue(result.successful)
    }

}