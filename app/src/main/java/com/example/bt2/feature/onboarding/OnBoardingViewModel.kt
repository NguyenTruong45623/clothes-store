package com.example.bt2.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardingViewModel: ViewModel()  {

    private var _navigateToSignIn = MutableStateFlow(false)
    val navigateToSignIn : StateFlow<Boolean> = _navigateToSignIn.asStateFlow()

    fun onClickBack(viewPager: ViewPager2)  {
        if(viewPager.currentItem > 0)
            viewPager.currentItem -= 1
    }

    fun onClickForward(viewPager: ViewPager2) {
        if(viewPager.currentItem < 2){
            viewPager.currentItem += 1
        } else {
            _navigateToSignIn.value = true
        }

    }

    fun onClickNext(viewPager: ViewPager2) {
        viewPager.currentItem += 1
    }

    fun onNavigationComplete() {
        _navigateToSignIn.value = false
    }
}