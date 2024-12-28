package com.example.bt2.feature.onboarding

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.bt2.R
import com.example.bt2.feature.signIn.SignInFragment

class OnBoardingViewModel: ViewModel()  {

    fun onClickBack(viewPager: ViewPager2)  {
        if(viewPager.currentItem > 0)
            viewPager.currentItem -= 1
    }

    fun onClickForward(viewPager: ViewPager2, fragmentManager: FragmentManager) {
        if(viewPager.currentItem < 2){
            viewPager.currentItem += 1
        } else {
            fragmentManager.commit {
                replace(R.id.fragment_container, SignInFragment())
                addToBackStack(null)
            }
        }

    }

    fun onClickNext(viewPager: ViewPager2) {
        viewPager.currentItem += 1
    }
}