package com.example.bt2.feature.signIn

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bt2.R
import com.example.bt2.repository.local.UserDataProfileStore
import com.example.bt2.repository.local.UserDataStore
import com.example.bt2.databinding.FragmentSignInBinding
import com.example.bt2.until.makeLinkClickable


class SignInFragment : Fragment() {

    private lateinit var userDataStore: UserDataStore
    private lateinit var userProfileDataStore: UserDataProfileStore
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel : SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        userDataStore = UserDataStore(requireContext())
        userProfileDataStore = UserDataProfileStore(requireContext())
        viewModel = SignInViewModel(userDataStore,userProfileDataStore)
        binding.viewModel = viewModel

        makeLinkClickable(binding.tvSignUp, "Sign Up", Color.BLUE)
        makeLinkClickable(binding.forgotPW, "Forgot Password?", Color.BLUE)



        return binding.root
    }


}