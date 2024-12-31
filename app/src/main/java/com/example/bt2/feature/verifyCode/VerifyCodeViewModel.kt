package com.example.bt2.feature.verifyCode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class VerifyCodeViewModel : ViewModel() {

    private val _navigateToNewPassWord = MutableSharedFlow<Unit>()
    val navigateToNewPassWord: SharedFlow<Unit> = _navigateToNewPassWord.asSharedFlow()

    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack.asSharedFlow()


    fun onClickVerifyButton() {
        viewModelScope.launch {
            _navigateToNewPassWord.emit(Unit)
        }
    }

    fun onClickBackButton() {
        viewModelScope.launch {
            _navigateBack.emit(Unit)
        }
    }

}