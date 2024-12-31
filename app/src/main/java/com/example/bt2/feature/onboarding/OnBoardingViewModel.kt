package com.example.bt2.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel: ViewModel()  {

    private var _navigateToSignIn = MutableSharedFlow<Unit>()
    val navigateToSignIn : SharedFlow<Unit> = _navigateToSignIn.asSharedFlow()

    fun onClickBack(viewPager: ViewPager2)  {
        if(viewPager.currentItem > 0)
            viewPager.currentItem -= 1
    }

    fun onClickForward(viewPager: ViewPager2) {
        if(viewPager.currentItem < 2){
            viewPager.currentItem += 1
        } else {
            viewModelScope.launch {
                _navigateToSignIn.emit(Unit)
            }
        }
    }

    fun onClickNext(viewPager: ViewPager2) {
        viewPager.currentItem += 1
    }

}