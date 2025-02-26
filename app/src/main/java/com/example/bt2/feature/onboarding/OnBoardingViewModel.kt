package com.example.bt2.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.bt2.feature.createAccount.CreateAccountUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardingViewModel: ViewModel()  {
    private val _formState = MutableStateFlow(OnBoardingUiState())
    val formState: StateFlow<OnBoardingUiState> = _formState.asStateFlow()

    fun onClickBack(viewPager: ViewPager2)  {
        if(viewPager.currentItem > 0)
            viewPager.currentItem -= 1
    }

    fun onClickForward(viewPager: ViewPager2) {
        if(viewPager.currentItem < 2){
            viewPager.currentItem += 1
        } else {
            _formState.update { it.copy(isClickToSignIn = true) }
        }
    }

    fun onClickNext(viewPager: ViewPager2) {
        viewPager.currentItem += 1
    }

    fun onNavigationComplete() {
        _formState.update { it.copy(isClickToSignIn = false) }
    }
}