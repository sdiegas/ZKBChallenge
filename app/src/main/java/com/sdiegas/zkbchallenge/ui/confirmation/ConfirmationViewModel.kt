package com.sdiegas.zkbchallenge.ui.confirmation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfirmationViewModel(initState: ConfirmationViewState): ViewModel() {

    var state = MutableLiveData(initState)

}