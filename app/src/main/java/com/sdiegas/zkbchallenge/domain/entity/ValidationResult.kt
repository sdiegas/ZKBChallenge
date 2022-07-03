package com.sdiegas.zkbchallenge.domain.entity

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
