package com.sdiegas.zkbchallenge.ui.registration

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime

class RegistrationFormStateTest {

    @Test
    fun registrationFormStateInit() {
        val testRegistrationFormState = RegistrationFormState()

        Assert.assertEquals("", testRegistrationFormState.name)
        Assert.assertNull(testRegistrationFormState.nameError)
        Assert.assertEquals("", testRegistrationFormState.email)
        Assert.assertNull(testRegistrationFormState.emailError)
        Assert.assertTrue(testRegistrationFormState.birthdayDate.isBefore(LocalDateTime.now()))
        Assert.assertTrue(testRegistrationFormState.birthdayDate.isAfter(LocalDateTime.now().minusSeconds(1)))
        Assert.assertNull(testRegistrationFormState.birthdayDateError)
    }
}