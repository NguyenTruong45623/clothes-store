package com.example.bt2.feature.newPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewPasswordViewModel : ViewModel() {
    private val _formState = MutableStateFlow(NewPasswordUiState())
    var formState = _formState.asStateFlow()

    private val _clearConfirmPasswordInput = MutableStateFlow(false)
    val clearConfirmPasswordInput = _clearConfirmPasswordInput.asStateFlow()


    fun onPasswordChanged(newPassword: String) {
        _formState.update { it.copy(password = newPassword)}
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        if (_clearConfirmPasswordInput.value) {
            _clearConfirmPasswordInput.value = false
        }
        _formState.update { it.copy(confirmPassword = newConfirmPassword)}
    }

    fun onClickNewPassWord() {

        if (_formState.value.password.length < 8) {
            _formState.update { currentState ->
                currentState.copy(
                    passwordError = "Mật khẩu phải có ít nhất 8 ký tự",
                )
            }
        } else if (_formState.value.confirmPassword != _formState.value.password) {
            _formState.update { currentState ->
                currentState.copy(
                    confirmPasswordError = "Mật khẩu không khớp",
                    confirmPassword = "",
                    passwordError = null
                )
            }
            _clearConfirmPasswordInput.value = true
        } else {
            _formState.update { currentState ->
                currentState.copy(
                    passwordError = null,
                    confirmPasswordError = null
                )
            }
        }

    }

    fun onClickBack() {
        _formState.update { it.copy( isClickBack = true ) }
    }

    fun onNavigationComplete() {
        _formState.update { it.copy(isClickBack = false) }
    }
}