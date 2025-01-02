package com.example.bt2.feature.verifyCode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VerifyCodeViewModel : ViewModel() {

    private val _formState = MutableStateFlow(VerifyCodeState())
    val formState: StateFlow<VerifyCodeState> = _formState.asStateFlow()


    fun onClickVerifyButton() {
        _formState.update { it.copy(isClickToNewPassword = true) }
    }

    fun onClickBackButton() {
        _formState.update { it.copy(isClickBack = true) }
    }

    fun onNavigationComplete() {
        _formState.update { it.copy(isClickToNewPassword = false,isClickBack = false) }
    }
}