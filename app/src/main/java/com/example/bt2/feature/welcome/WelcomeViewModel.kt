package com.example.bt2.feature.welcome

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.feature.onboarding.OnBoardingFragment
import com.example.bt2.feature.signIn.SignInFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WelcomeViewModel : ViewModel() {

    private val _navigateToSignIn = MutableStateFlow(false)
    val navigateToSignIn: StateFlow<Boolean> = _navigateToSignIn.asStateFlow()
    private val _navigateToOnBoarding = MutableStateFlow(false)
    val navigateToOnBoarding: StateFlow<Boolean> = _navigateToOnBoarding.asStateFlow()

    fun onClickButtonLetStart() {
        _navigateToOnBoarding.value = true
    }

    fun onClickToSignIn() {
        _navigateToSignIn.value = true
    }

    fun onNavigationComplete() {
        _navigateToSignIn.value = false
        _navigateToOnBoarding.value = false
    }
}
