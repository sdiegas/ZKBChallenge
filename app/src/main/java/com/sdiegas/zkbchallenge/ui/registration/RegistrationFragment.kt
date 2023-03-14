package com.sdiegas.zkbchallenge.ui.registration

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sdiegas.zkbchallenge.databinding.FragmentRegistrationBinding
import com.sdiegas.zkbchallenge.util.Constants
import com.sdiegas.zkbchallenge.util.toConfirmationViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by viewModels()

    private var _binding: FragmentRegistrationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(
            inflater,
            container,
            false
        )

        _binding?.registrationViewModel = registrationViewModel

        _binding?.lifecycleOwner = viewLifecycleOwner

        setValidationEventListener()

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendarImageClickListener()
    }

    private fun setCalendarImageClickListener() {
        binding.imageView.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(requireContext())
        registrationViewModel.state.value.birthdayDate.let {
            datePickerDialog.updateDate(it.year, it.month.value, it.dayOfMonth)
        }
        datePickerDialog.datePicker.maxDate =
            Constants.LocalDateTimes.maxLocalDateTime.atZone(ZoneId.of("Europe/Zurich"))
                .toInstant().toEpochMilli()
        datePickerDialog.datePicker.minDate =
            Constants.LocalDateTimes.minLocalDateTime.atZone(ZoneId.of("Europe/Zurich"))
                .toInstant().toEpochMilli()
        datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            registrationViewModel.state.update {
                it.copy(
                    birthdayDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                )
            }
        }
        datePickerDialog.show()
    }

    private fun setValidationEventListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            registrationViewModel.validationEvents.collect { event ->
                when (event) {
                    is RegistrationViewModel.ValidationEvent.Success -> {
                        val action =
                            RegistrationFragmentDirections.actionRegistrationFragmentToConfirmationFragment(
                                registrationViewModel.state.value.toConfirmationViewState()
                            )
                        registrationViewModel.resetData()
                        view?.findNavController()?.navigate(action)
                    }
                }
            }
        }
    }


}