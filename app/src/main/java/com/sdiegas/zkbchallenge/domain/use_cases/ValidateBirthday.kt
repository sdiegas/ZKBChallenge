package com.sdiegas.zkbchallenge.domain.use_cases

import com.sdiegas.zkbchallenge.util.maxLocalDateTime
import com.sdiegas.zkbchallenge.util.minLocalDateTime
import java.time.LocalDateTime

class ValidateBirthday {

    fun execute(birthday: LocalDateTime): ValidationResult {
        if ((birthday.isAfter(maxLocalDateTime)) || (birthday.isBefore(minLocalDateTime))) {
            return ValidationResult(
                successful = false,
                errorMessage = "Date must be between 01.01.1900 and 31.12.2021"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}