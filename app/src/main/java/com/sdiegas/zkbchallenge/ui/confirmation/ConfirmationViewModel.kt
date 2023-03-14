package com.sdiegas.zkbchallenge.ui.confirmation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ConfirmationViewModel(initState: ConfirmationViewState): ViewModel() {

    var state = MutableStateFlow(initState)

}