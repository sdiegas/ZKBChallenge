package com.sdiegas.zkbchallenge.domain.usecase

import com.sdiegas.zkbchallenge.domain.entity.ValidationResult
import com.sdiegas.zkbchallenge.util.Constants

class ValidateEmail {
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = Constants.ErrorMessages.validateEmailErrorEmpty
            )
        }
        if (!email.matches(emailPattern)) {
            return ValidationResult(
                successful = false,
                errorMessage = Constants.ErrorMessages.validateEmailErrorInvalid
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}