package com.sdiegas.zkbchallenge.domain.usecase

import com.sdiegas.zkbchallenge.domain.entity.ValidationResult
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