package com.sdiegas.zkbchallenge.util

import java.time.LocalDateTime
import java.time.Month

interface Constants {

    interface ErrorMessages {
        companion object {
            const val validateNameErrorEmpty = "The name can't be blank"
            const val validateEmailErrorEmpty = "The email can't be blank"
            const val validateEmailErrorInvalid = "The email is not valid"
            const val validateBirthdayInvalid = "Date must be between 01.01.1900 and 31.12.2021"
        }
    }

    interface LocalDateTimes {
        companion object {
            val minLocalDateTime = LocalDateTime.of(1900, Month.JANUARY, 1, 0, 0)
            val maxLocalDateTime = LocalDateTime.of(2021, Month.DECEMBER, 31, 23, 59)
        }

    }
}

