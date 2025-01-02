package com.example.bt2.feature.signIn

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentSignInBinding
import com.example.bt2.feature.onboarding.OnBoardingFragmentDirections
import com.example.bt2.until.makeLinkClickable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.formState.collectLatest { state ->
                    if (state.isClickSignUp) {
                        val action = SignInFragmentDirections.actionSignInFragmentToCreateAccountFragment()
                        findNavController().navigate(action)
                    }

                    if (state.isClickVerifyPassword) {
                        val action = SignInFragmentDirections.actionSignInFragmentToVerifyCodeFragment()
                        findNavController().navigate(action)
                    }

                    viewModel.onNavigationComplete()
                }
            }
        }


        return binding.root
    }


}