package com.example.bt2.feature.signIn

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentSignInBinding
import com.example.bt2.until.makeLinkClickable
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

        makeLinkClickable(binding.tvSignIn, "Sign Up", Color.BLUE)
        makeLinkClickable(binding.forgotPW, "Forgot Password?", Color.BLUE)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToCreateAccount.collect{ navigate ->
                    if (navigate) {
                        findNavController().navigate(R.id.action_signInFragment_to_createAccountFragment)
                        viewModel.onNavigationComplete()
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToVerifyCode.collect{ navigate ->
                    if (navigate) {
                        findNavController().navigate(R.id.action_signInFragment_to_verifyCodeFragment)
                        viewModel.onNavigationComplete()
                    }
                }
            }
        }

        return binding.root
    }


}