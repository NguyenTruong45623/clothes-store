package com.example.bt2.feature.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {

    private val _navigateToSignIn = MutableSharedFlow<Unit>()
    val navigateToSignIn: SharedFlow<Unit> = _navigateToSignIn.asSharedFlow()
    private val _navigateToOnBoarding = MutableSharedFlow<Unit>()
    val navigateToOnBoarding: SharedFlow<Unit> = _navigateToOnBoarding.asSharedFlow()

    fun onClickButtonLetStart() {
        viewModelScope.launch {
            _navigateToOnBoarding.emit(Unit)
        }
    }

    fun onClickToSignIn() {
        viewModelScope.launch {
            _navigateToSignIn.emit(Unit)
        }
    }

}
