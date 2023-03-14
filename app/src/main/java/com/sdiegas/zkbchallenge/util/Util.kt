package com.sdiegas.zkbchallenge.util

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}

@BindingAdapter("app:localDateTime")
fun setLocalDateTime(view: TextInputLayout, localDateTime: LocalDateTime?) {
    view.editText?.setText(localDateTime?.format(localDateTimeFormatter))
}

var localDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")