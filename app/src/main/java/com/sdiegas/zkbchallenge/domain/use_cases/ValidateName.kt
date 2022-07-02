package com.sdiegas.zkbchallenge.domain.use_cases

class ValidateName {

    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}