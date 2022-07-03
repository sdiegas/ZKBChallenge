package com.sdiegas.zkbchallenge.ui.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConfirmationViewModelFactory(private val confirmationViewState: ConfirmationViewState) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmationViewModel::class.java)) {
            return ConfirmationViewModel(confirmationViewState) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}