package com.sdiegas.zkbchallenge.data.local

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationData(
    var name: String = "",
    var email: String = "",
    var birthday: String = ""
)
