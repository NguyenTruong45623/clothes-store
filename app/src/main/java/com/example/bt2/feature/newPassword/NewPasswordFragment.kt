package com.example.bt2.feature.newPassword

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentNewPasswordBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NewPasswordFragment : Fragment() {

    private lateinit var binding : FragmentNewPasswordBinding
    private val viewModel : NewPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_password, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModel.navigateBack.onEach {
            findNavController().popBackStack()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}