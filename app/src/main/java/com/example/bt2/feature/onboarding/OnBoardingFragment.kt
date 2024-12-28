package com.example.bt2.feature.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.bt2.R
import com.example.bt2.databinding.FragmentOnBoardingBinding
import com.example.bt2.feature.onboarding.ViewPage.OnBoarding1Fragment
import com.example.bt2.feature.onboarding.ViewPage.OnBoarding2Fragment
import com.example.bt2.feature.onboarding.ViewPage.OnBoarding3Fragment
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false)

        binding.viewModel = viewModel
        binding.viewpager2 = binding.introViewPager
        binding.fm = parentFragmentManager


        val fragmentList = arrayListOf<Fragment>(
            OnBoarding1Fragment(),
            OnBoarding2Fragment(),
            OnBoarding3Fragment())

        val adapter = OnboardingAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.introViewPager.adapter = adapter

        TabLayoutMediator(binding.intoTabLayout, binding.introViewPager) { tab, position ->}.attach()

        binding.introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Cập nhật trạng thái của nút backOnBoarding
                if (position == 0) {

                    binding.backOnBoarding.alpha = 0f
                    binding.backOnBoarding.isEnabled = false
                } else {
                    binding.backOnBoarding.alpha = 1f
                    binding.backOnBoarding.isEnabled = true
                }

                if (position == 2) {
                    binding.tvNext.visibility = View.GONE
                } else {
                    binding.tvNext.visibility = View.VISIBLE
                }
            }
        })

        return binding.root
    }


}