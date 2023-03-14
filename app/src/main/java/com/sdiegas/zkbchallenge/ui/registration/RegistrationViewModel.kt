package com.sdiegas.zkbchallenge.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdiegas.zkbchallenge.data.local.RegistrationData
import com.sdiegas.zkbchallenge.domain.usecase.PersistRegistrationData
import com.sdiegas.zkbchallenge.domain.usecase.ValidateBirthday
import com.sdiegas.zkbchallenge.domain.usecase.ValidateEmail
import com.sdiegas.zkbchallenge.domain.usecase.ValidateName
import com.sdiegas.zkbchallenge.util.localDateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateNameUseCase: ValidateName,
    private val validateEmailUseCase: ValidateEmail,
    private val validateBirthday: ValidateBirthday,
    private val persistRegistrationData: PersistRegistrationData,
) : ViewModel() {

    var state = MutableStateFlow(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        loadPersistedData()
    }

    fun validate() {
        state.value.let { viewState ->
            val nameResult = validateNameUseCase.execute(viewState.name)
            val emailResult = validateEmailUseCase.execute(viewState.email)
            val birthdayResult = validateBirthday.execute(viewState.birthdayDate)

            val hasError = listOf(
                nameResult,
                emailResult,
                birthdayResult
            ).any { !it.successful }

            if (hasError) {
                state.update {
                    it.copy(
                        nameError = nameResult.errorMessage,
                        emailError = emailResult.errorMessage,
                        birthdayDateError = birthdayResult.errorMessage
                    )
                }
            } else {
                persistRegistrationData.save(
                    RegistrationData(
                        viewState.name, viewState.email, viewState.birthdayDate.format(
                            localDateTimeFormatter
                        )
                    )
                )
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.Success)
                }
            }
        }
    }

    fun resetData() {
        state.value = RegistrationFormState()
    }

    private fun loadPersistedData() {
        persistRegistrationData.load()?.let { registrationData ->
            state.update {
                it.copy(
                    name = registrationData.name,
                    email = registrationData.email
                )
            }
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

}