package com.example.bt2.feature.verifyCode

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.bt2.R
import com.example.bt2.databinding.FragmentVerifyCodeBinding

class VerifyCodeFragment : Fragment() {

    private lateinit var  binding : FragmentVerifyCodeBinding
    private val viewModel: VerifyCodeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verify_code, container, false)

        binding.viewModel = viewModel
        binding.fm = parentFragmentManager


        return binding.root
    }
}