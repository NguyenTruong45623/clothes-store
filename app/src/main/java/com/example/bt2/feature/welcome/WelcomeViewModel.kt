package com.example.bt2.feature.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2.feature.verifyCode.VerifyCodeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {

    private val _formState = MutableStateFlow(WelcomeUiState())
    val formState: StateFlow<WelcomeUiState> = _formState.asStateFlow()

    fun onClickButtonLetStart() {
        _formState.update { it.copy(isClickToOnBoarding = true) }
    }

    fun onClickToSignIn() {
        _formState.update { it.copy(isClickToSignIn = true) }
    }

    fun onNavigationComplete() {
        _formState.update { it.copy(isClickToOnBoarding = false, isClickToSignIn = false) }
    }

}
