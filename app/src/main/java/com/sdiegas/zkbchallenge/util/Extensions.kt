package com.sdiegas.zkbchallenge.util

import com.sdiegas.zkbchallenge.ui.confirmation.ConfirmationViewState
import com.sdiegas.zkbchallenge.ui.registration.RegistrationFormState

fun RegistrationFormState.toConfirmationViewState() = ConfirmationViewState(
    name = name,
    email = email,
    birthday = birthdayDate.format(localDateTimeFormatter)
)