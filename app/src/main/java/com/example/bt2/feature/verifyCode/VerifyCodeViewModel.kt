package com.example.bt2.feature.verifyCode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VerifyCodeViewModel : ViewModel() {

    private val _navigateToNewPassWord = MutableStateFlow(false)
    val navigateToNewPassWord: StateFlow<Boolean> = _navigateToNewPassWord.asStateFlow()

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack: StateFlow<Boolean> = _navigateBack.asStateFlow()


    fun onClickVerifyButton() {
        _navigateToNewPassWord.value = true
    }

    fun onClickBackButton() {
        _navigateBack.value = true
    }

    fun onNavigationComplete() {
        _navigateToNewPassWord.value = false
        _navigateBack.value = false
    }
}