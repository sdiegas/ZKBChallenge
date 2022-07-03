package com.sdiegas.zkbchallenge.ui.confirmation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmationViewState(
    var name: String = "",
    var email: String = "",
    var birthday: String = ""
) : Parcelable
