package com.example.bt2.feature.verifyCode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentVerifyCodeBinding
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToNewPassWord.collect{ navigate ->
                    if (navigate) {
                        findNavController().navigate(R.id.action_verifyCodeFragment_to_newPasswordFragment)
                        viewModel.onNavigationComplete()
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateBack.collect{ navigate ->
                    if (navigate) {
                        findNavController().popBackStack()
                        viewModel.onNavigationComplete()
                    }
                }
            }
        }

        return binding.root
    }
}