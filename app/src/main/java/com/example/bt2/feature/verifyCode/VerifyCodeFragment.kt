package com.example.bt2.feature.verifyCode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentVerifyCodeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class VerifyCodeFragment : Fragment() {

    private lateinit var  binding : FragmentVerifyCodeBinding
    private val viewModel: VerifyCodeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verify_code, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModel.navigateToNewPassWord.onEach {
            val action = VerifyCodeFragmentDirections.actionVerifyCodeFragmentToNewPasswordFragment()
            findNavController().navigate(action)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.navigateBack.onEach {
            findNavController().popBackStack()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}