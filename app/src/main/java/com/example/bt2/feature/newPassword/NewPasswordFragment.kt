package com.example.bt2.feature.newPassword

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentNewPasswordBinding
import com.example.bt2.databinding.FragmentVerifyCodeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewPasswordFragment : Fragment() {

    private lateinit var binding : FragmentNewPasswordBinding
    private val viewModel : NewPassWordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_password, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

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