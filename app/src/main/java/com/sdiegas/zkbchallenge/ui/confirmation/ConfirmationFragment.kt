package com.sdiegas.zkbchallenge.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sdiegas.zkbchallenge.databinding.FragmentConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationFragment : Fragment() {

    private lateinit var confirmationViewModel: ConfirmationViewModel

    private var _binding: FragmentConfirmationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModelFactory = ConfirmationViewModelFactory(ConfirmationFragmentArgs.fromBundle(requireArguments()).confirmationViewState ?: ConfirmationViewState())
        confirmationViewModel = ViewModelProvider(this, viewModelFactory)[ConfirmationViewModel::class.java]

        _binding?.confirmationViewModel = confirmationViewModel

        _binding?.lifecycleOwner = viewLifecycleOwner

        return  binding.root
    }

}