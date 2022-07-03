package com.sdiegas.zkbchallenge.domain.usecase

import com.sdiegas.zkbchallenge.data.local.RegistrationData
import com.sdiegas.zkbchallenge.data.local.RegistrationDataStore
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class PersistRegistrationDataTest {
    private val registrationDataStore = mockk<RegistrationDataStore>()
    private val persistRegistrationData = PersistRegistrationData(registrationDataStore)
    private val testRegistrationData = RegistrationData("Stefan", "a@b.ch", "06.06.1993")

    @Test
    fun persistRegistrationDataSaveSuccess() {
        every { registrationDataStore.setRegistrationData(testRegistrationData) } returns Unit

        persistRegistrationData.save(testRegistrationData)

        verify { registrationDataStore.setRegistrationData(testRegistrationData) }
    }

    @Test
    fun persistRegistrationDataLoadSuccess() {
        every { registrationDataStore.getRegistrationData() } returns testRegistrationData

        val result = persistRegistrationData.load()

        verify { registrationDataStore.getRegistrationData() }
        Assert.assertEquals(testRegistrationData, result)
    }
}