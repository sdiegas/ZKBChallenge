package com.sdiegas.zkbchallenge.ui.registration

import java.time.LocalDateTime

data class RegistrationFormState(
    var name: String = "",
    var nameError: String? = null,
    var email: String = "",
    var emailError: String? = null,
    var birthdayDate: LocalDateTime = LocalDateTime.now(),
    var birthdayDateError: String? = null
)
