package com.sdiegas.zkbchallenge.domain.use_cases

import com.sdiegas.zkbchallenge.util.Constants

class ValidateName {

    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = Constants.ErrorMessages.validateNameErrorEmpty
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}