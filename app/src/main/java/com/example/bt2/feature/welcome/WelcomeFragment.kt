package com.example.bt2.feature.welcome

import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Html.fromHtml
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentWelcomeBinding
import com.example.bt2.feature.verifyCode.VerifyCodeFragmentDirections
import com.example.bt2.until.makeLinkClickable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        makeLinkClickable(binding.tvSignIn, "Sign In", Color.BLUE)
        binding.tvTitleTheFashionApp.text = fromHtml("<font color=${Color.BLACK}>The </font> <font color=${Color.parseColor("#795548")}>Fashion App</font><font color=${Color.BLACK}> That\n Makes You Look Your Best </font>")

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.formState.collectLatest { state ->
                    if (state.isClickToOnBoarding) {
                        val action = WelcomeFragmentDirections.actionWelcomeFragmentToOnBoardingFragment()
                        findNavController().navigate(action)
                    }

                    if (state.isClickToSignIn) {
                        val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment()
                        findNavController().navigate(action)
                    }

                    viewModel.onNavigationComplete()
                }
            }
        }

        return binding.root
    }
}