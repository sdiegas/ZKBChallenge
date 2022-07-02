package com.sdiegas.zkbchallenge.ui.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sdiegas.zkbchallenge.MainDispatcherRule
import com.sdiegas.zkbchallenge.domain.use_cases.ValidateBirthday
import com.sdiegas.zkbchallenge.domain.use_cases.ValidateEmail
import com.sdiegas.zkbchallenge.domain.use_cases.ValidateName
import com.sdiegas.zkbchallenge.domain.use_cases.ValidationResult
import com.sdiegas.zkbchallenge.getOrAwaitValue
import com.sdiegas.zkbchallenge.util.Constants
import com.sdiegas.zkbchallenge.util.mutation
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month

class RegistrationViewModelTest {
    private lateinit var validateNameUseCase: ValidateName
    private lateinit var validateEmailUseCase: ValidateEmail
    private lateinit var validateBirthdayUseCase: ValidateBirthday
    private lateinit var viewModel: RegistrationViewModel

    private val testNameValid = "Stefan"
    private val testNameEmpty = ""
    private val testEmailValid = "a@b.ch"
    private val testEmailEmpty = ""
    private val testEmailInvalid = "stefan@"
    private val testBirthdayValid = LocalDateTime.of(1993, Month.JUNE, 6, 9, 30)
    private val testBirthdayInvalid = LocalDateTime.now()

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
        viewModel = RegistrationViewModel(validateNameUseCase, validateEmailUseCase, validateBirthdayUseCase)
    }

    @Test
    fun test_ReturnValidationResult_OnValidName_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(testNameValid) } returns ValidationResult(true)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.mutation {
            it.value?.name = testNameValid
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testNameValid, viewModel.state.getOrAwaitValue().name)
        assertNull(viewModel.state.getOrAwaitValue().nameError)
    }

    @Test
    fun test_ReturnValidationResult_OnEmptyName_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(testNameEmpty) } returns ValidationResult(false, Constants.ErrorMessages.validateNameErrorEmpty)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.mutation {
            it.value?.name = testNameEmpty
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testNameEmpty, viewModel.state.getOrAwaitValue().name)
        assertEquals(Constants.ErrorMessages.validateNameErrorEmpty, viewModel.state.getOrAwaitValue().nameError)
    }

    @Test
    fun test_ReturnValidationResult_OnValidEmail_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(testEmailValid) } returns ValidationResult(true)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.mutation {
            it.value?.email = testEmailValid
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testEmailValid, viewModel.state.getOrAwaitValue().email)
        assertNull(viewModel.state.getOrAwaitValue().emailError)
    }

    @Test
    fun test_ReturnValidationResult_OnEmptyEmail_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(testEmailEmpty) } returns ValidationResult(false, Constants.ErrorMessages.validateEmailErrorEmpty)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.mutation {
            it.value?.email = testEmailEmpty
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testEmailEmpty, viewModel.state.getOrAwaitValue().email)
        assertEquals(Constants.ErrorMessages.validateEmailErrorEmpty, viewModel.state.getOrAwaitValue().emailError)
    }

    @Test
    fun test_ReturnValidationResult_OnInvalidEmail_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(testEmailInvalid) } returns ValidationResult(false, Constants.ErrorMessages.validateEmailErrorInvalid)
        every { validateBirthdayUseCase.execute(any()) } returns ValidationResult(false)
        //When
        viewModel.state.mutation {
            it.value?.email = testEmailInvalid
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testEmailInvalid, viewModel.state.getOrAwaitValue().email)
        assertEquals(Constants.ErrorMessages.validateEmailErrorInvalid, viewModel.state.getOrAwaitValue().emailError)
    }

    @Test
    fun test_ReturnValidationResult_OnValidBirthday_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(testBirthdayValid) } returns ValidationResult(true)
        //When
        viewModel.state.mutation {
            it.value?.birthdayDate = testBirthdayValid
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testBirthdayValid, viewModel.state.getOrAwaitValue().birthdayDate)
        assertNull(viewModel.state.getOrAwaitValue().birthdayDateError)
    }

    @Test
    fun test_ReturnValidationResult_OnInvalidBirthday_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(false)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(false)
        every { validateBirthdayUseCase.execute(testBirthdayInvalid) } returns ValidationResult(false, Constants.ErrorMessages.validateBirthdayInvalid)
        //When
        viewModel.state.mutation {
            it.value?.birthdayDate = testBirthdayInvalid
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertEquals(testBirthdayInvalid, viewModel.state.getOrAwaitValue().birthdayDate)
        assertEquals(Constants.ErrorMessages.validateBirthdayInvalid, viewModel.state.getOrAwaitValue().birthdayDateError)
    }

    @Test
    fun test_ReturnValidationResult_OnEverythingOK_LiveDataChanged() {
        //Given
        every { validateNameUseCase.execute(any()) } returns ValidationResult(true)
        every { validateEmailUseCase.execute(any()) } returns ValidationResult(true)
        every { validateBirthdayUseCase.execute(testBirthdayValid) } returns ValidationResult(true)
        //When
        viewModel.state.mutation {
            it.value?.name = testNameValid
            it.value?.email = testEmailValid
            it.value?.birthdayDate = testBirthdayValid
        }
        viewModel.validate()
        //Then
        verify { validateNameUseCase.execute(any()) }
        verify { validateEmailUseCase.execute(any()) }
        verify { validateBirthdayUseCase.execute(any()) }
        assertNull(viewModel.state.getOrAwaitValue().nameError)
        assertNull(viewModel.state.getOrAwaitValue().emailError)
        assertNull(viewModel.state.getOrAwaitValue().birthdayDateError)
    }
}