package com.example.bt2.feature.welcome

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2.R
import com.example.bt2.feature.onboarding.OnBoardingFragment
import com.example.bt2.feature.signIn.SignInFragment
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {

    fun onClickButtonLetStart(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace(R.id.fragment_container, OnBoardingFragment())
            addToBackStack(null)
        }
    }

    fun onClickToSignIn(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace(R.id.fragment_container, SignInFragment())
            addToBackStack(null)
        }
    }
}
