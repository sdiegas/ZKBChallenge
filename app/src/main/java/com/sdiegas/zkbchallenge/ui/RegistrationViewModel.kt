package com.sdiegas.zkbchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdiegas.zkbchallenge.domain.use_cases.ValidateEmail
import com.sdiegas.zkbchallenge.domain.use_cases.ValidateName
import com.sdiegas.zkbchallenge.util.mutation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateNameUseCase: ValidateName,
    private val validateEmailUseCase: ValidateEmail
    ): ViewModel() {

    var state = MutableLiveData(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun validate() {
        state.value?.let { viewState ->
            val nameResult = validateNameUseCase.execute(viewState.name)
            val emailResult = validateEmailUseCase.execute(viewState.email)

            val hasError = listOf(
                nameResult,
                emailResult
            ).any { !it.successful }

            if (hasError) {
                state.mutation {
                    it.value?.nameError = nameResult.errorMessage
                    it.value?.emailError = emailResult.errorMessage
                }
                return
            }
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }

}