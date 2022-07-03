package com.sdiegas.zkbchallenge.util

import androidx.lifecycle.MutableLiveData
import com.sdiegas.zkbchallenge.ui.confirmation.ConfirmationViewState
import com.sdiegas.zkbchallenge.ui.registration.RegistrationFormState

fun RegistrationFormState.toConfirmationViewState() = ConfirmationViewState(
    name = name,
    email = email,
    birthday = birthdayDate.format(localDateTimeFormatter)
)

fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}