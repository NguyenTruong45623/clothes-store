package com.example.bt2.feature.signIn

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentSignInBinding
import com.example.bt2.until.makeLinkClickable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel : SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        makeLinkClickable(binding.tvSignUp, "Sign Up", Color.BLUE)
        makeLinkClickable(binding.forgotPW, "Forgot Password?", Color.BLUE)

        viewModel.navigateToCreateAccount.onEach {
            val action = SignInFragmentDirections.actionSignInFragmentToCreateAccountFragment()
            findNavController().navigate(action)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.navigateToVerifyCode.onEach {
            val action = SignInFragmentDirections.actionSignInFragmentToVerifyCodeFragment()
            findNavController().navigate(action)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }


}