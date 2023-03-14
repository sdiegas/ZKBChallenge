package com.sdiegas.zkbchallenge.ui.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sdiegas.zkbchallenge.MainDispatcherRule
import com.sdiegas.zkbchallenge.data.local.RegistrationData
import com.sdiegas.zkbchallenge.domain.entity.ValidationResult
import com.sdiegas.zkbchallenge.domain.usecase.PersistRegistrationData
import com.sdiegas.zkbchallenge.domain.usecase.ValidateBirthday
import com.sdiegas.zkbchallenge.domain.usecase.ValidateEmail
import com.sdiegas.zkbchallenge.domain.usecase.ValidateName
import com.sdiegas.zkbchallenge.util.Constants
import com.sdiegas.zkbchallenge.util.localDateTimeFormatter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month

@OptIn(ExperimentalCoroutinesApi::class)
class RegistrationViewModelTest {
    private lateinit var validateNameUseCase: ValidateName
    private lateinit var validateEmailUseCase: ValidateEmail
    private lateinit var validateBirthdayUseCase: ValidateBirthday
    private lateinit var persistRegistrationDataUseCase: PersistRegistrationData
    private lateinit var viewModel: RegistrationViewModel

    private val testNameValid = "Stefan"
    private val testNameEmpty = ""
    private val testEmailValid = "a@b.ch"
    private val testEmailEmpty = ""
    private val testEmailInvalid = "stefan@"
    private val testBirthdayValid = LocalDateTime.of(1993, Month.JUNE, 6, 9, 30)
    private val testBirthdayInvalid = LocalDateTime.now()
    private val testRegistrationData = RegistrationData(name = testNameValid, email = testEmailValid, birthday = testBirthdayValid.format(
        localDateTimeFormatter))

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        validateNameUseCase = mockk()
        validateEmailUseCase = mockk()
        validateBirthdayUseCase = mockk()
        persistRegistrationDataUseCase = mockk()
        every { persistRegistrationDataUseCase.load() } returns null
        viewModel = RegistrationViewModel(validateNameUseCase, validateEmailUseCase, validateBirthdayUseCase, persistRegistrationDataUseCase)
    }

    @Test
    fun test_ReturnValidationResult_OnValidName_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(testNameValid) } returns ValidationResult(true)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.update {
            it.copy(name = testNameValid)
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testNameValid, viewModel.state.firstOrNull()?.name)
        assertNull(viewModel.state.firstOrNull()?.nameError)
    }

    @Test
    fun test_ReturnValidationResult_OnEmptyName_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(testNameEmpty) } returns ValidationResult(false, Constants.ErrorMessages.validateNameErrorEmpty)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.update {
            it.copy(name = testNameEmpty)
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testNameEmpty, viewModel.state.firstOrNull()?.name)
        assertEquals(Constants.ErrorMessages.validateNameErrorEmpty, viewModel.state.firstOrNull()?.nameError)
    }

    @Test
    fun test_ReturnValidationResult_OnValidEmail_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(testEmailValid) } returns ValidationResult(true)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.update {
            it.copy(email = testEmailValid)
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testEmailValid, viewModel.state.firstOrNull()?.email)
        assertNull(viewModel.state.firstOrNull()?.emailError)
    }

    @Test
    fun test_ReturnValidationResult_OnEmptyEmail_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(testEmailEmpty) } returns ValidationResult(false, Constants.ErrorMessages.validateEmailErrorEmpty)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.update {
            it.copy(email = testEmailEmpty)
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testEmailEmpty, viewModel.state.firstOrNull()?.email)
        assertEquals(Constants.ErrorMessages.validateEmailErrorEmpty, viewModel.state.firstOrNull()?.emailError)
    }

    @Test
    fun test_ReturnValidationResult_OnInvalidEmail_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(testEmailInvalid) } returns ValidationResult(false, Constants.ErrorMessages.validateEmailErrorInvalid)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.update {
            it.copy(email = testEmailInvalid)
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testEmailInvalid, viewModel.state.firstOrNull()?.email)
        assertEquals(Constants.ErrorMessages.validateEmailErrorInvalid, viewModel.state.firstOrNull()?.emailError)
    }

    @Test
    fun test_ReturnValidationResult_OnValidBirthday_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(testBirthdayValid) } returns ValidationResult(true)
        //When
        viewModel.state.update {
            it.copy(birthdayDate = testBirthdayValid)
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testBirthdayValid, viewModel.state.firstOrNull()?.birthdayDate)
        assertNull(viewModel.state.firstOrNull()?.birthdayDateError)
    }

    @Test
    fun test_ReturnValidationResult_OnInvalidBirthday_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(testBirthdayInvalid) } returns ValidationResult(false, Constants.ErrorMessages.validateBirthdayInvalid)
        //When
        viewModel.state.update {
            it.copy(birthdayDate = testBirthdayInvalid)
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testBirthdayInvalid, viewModel.state.firstOrNull()?.birthdayDate)
        assertEquals(Constants.ErrorMessages.validateBirthdayInvalid, viewModel.state.firstOrNull()?.birthdayDateError)
    }

    @Test
    fun test_ReturnValidationResult_OnEverythingOK_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(true)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(true)
        every { validateBirthdayUseCase.execute(testBirthdayValid) } returns ValidationResult(true)
        every { persistRegistrationDataUseCase.save(testRegistrationData) } returns Unit
        //When
        viewModel.state.update {
            it.copy(
                name = testNameValid,
                email = testEmailValid,
                birthdayDate = testBirthdayValid
            )
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertNull(viewModel.state.firstOrNull()?.nameError)
        assertNull(viewModel.state.firstOrNull()?.emailError)
        assertNull(viewModel.state.firstOrNull()?.birthdayDateError)
    }

    @Test
    fun test_PersistRegistrationData_OnEverythingOK_LiveDataChanged() = runTest {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(true)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(true)
        every { validateBirthdayUseCase.execute(testBirthdayValid) } returns ValidationResult(true)
        every { persistRegistrationDataUseCase.save(testRegistrationData) } returns Unit
        //When
        viewModel.state.update {
            it.copy(
                name = testNameValid,
                email = testEmailValid,
                birthdayDate = testBirthdayValid
            )
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        verify { persistRegistrationDataUseCase.save(testRegistrationData) }
        assertNull(viewModel.state.firstOrNull()?.nameError)
        assertNull(viewModel.state.firstOrNull()?.emailError)
        assertNull(viewModel.state.firstOrNull()?.birthdayDateError)
    }
}