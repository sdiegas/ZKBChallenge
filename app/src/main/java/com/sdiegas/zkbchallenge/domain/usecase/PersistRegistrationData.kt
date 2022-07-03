package com.sdiegas.zkbchallenge.domain.usecase

import com.sdiegas.zkbchallenge.data.local.RegistrationData
import com.sdiegas.zkbchallenge.data.local.RegistrationDataStore
import javax.inject.Inject

class PersistRegistrationData @Inject constructor(
    private val registrationDataStore: RegistrationDataStore
) {

    fun save(registrationData: RegistrationData) {
        registrationDataStore.setRegistrationData(registrationData)
    }

    fun load(): RegistrationData? {
        return registrationDataStore.getRegistrationData()
    }
}