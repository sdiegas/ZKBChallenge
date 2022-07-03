package com.sdiegas.zkbchallenge.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
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