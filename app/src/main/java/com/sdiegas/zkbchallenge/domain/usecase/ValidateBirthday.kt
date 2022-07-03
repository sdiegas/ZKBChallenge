package com.sdiegas.zkbchallenge.domain.usecase

import com.sdiegas.zkbchallenge.domain.entity.ValidationResult
import com.sdiegas.zkbchallenge.util.Constants
import java.time.LocalDateTime

class ValidateBirthday {

    fun execute(birthday: LocalDateTime): ValidationResult {
        if ((birthday.isAfter(Constants.LocalDateTimes.maxLocalDateTime)) || (birthday.isBefore(Constants.LocalDateTimes.minLocalDateTime))) {
            return ValidationResult(
                successful = false,
                errorMessage = Constants.ErrorMessages.validateBirthdayInvalid
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}