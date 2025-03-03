package com.example.bt2.feature.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class OnboardingAdapter(list: ArrayList<Fragment>,
                        fm: FragmentManager,
                        lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList = list

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}