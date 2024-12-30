package com.example.bt2.feature.createAccount

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
import com.example.bt2.databinding.FragmentAccountCreateBinding
import kotlinx.coroutines.launch

class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountCreateBinding
    private val viewModel: CreateAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_create, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToProfile.collect { navigate ->
                    if (navigate) {
                        findNavController().navigate(R.id.action_createAccountFragment_to_yourProfileFragment)
                        viewModel.onNavigationComplete()
                    }
                }
            }
        }
        return binding.root
    }
}