package com.sdiegas.zkbchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sdiegas.zkbchallenge.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            registrationViewModel.validationEvents.collect { event ->
                when(event) {
                    is RegistrationViewModel.ValidationEvent.Success -> {
                        val action =
                            RegistrationFragmentDirections.actionRegistrationFragmentToConfirmationFragment()
                        view?.findNavController()?.navigate(action)
                    }
                }
            }
        }

        return  binding.root
    }

}