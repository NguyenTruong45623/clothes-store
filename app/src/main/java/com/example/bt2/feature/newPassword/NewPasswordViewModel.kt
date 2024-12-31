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
    private val _formData = MutableStateFlow(NewPasswordUiState())
    var formData = _formData.asStateFlow()

    private val _clearConfirmPasswordInput = MutableStateFlow(false)
    val clearConfirmPasswordInput = _clearConfirmPasswordInput.asStateFlow()

    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: SharedFlow<Unit> = _navigateBack.asSharedFlow()

    fun onPasswordChanged(newPassword: String) {
        _formData.update { it.copy(password = newPassword)}
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        if (_clearConfirmPasswordInput.value) {
            _clearConfirmPasswordInput.value = false
        }
        _formData.update { it.copy(confirmPassword = newConfirmPassword)}
    }

    fun onClickNewPassWord() {

        if (_formData.value.password.length < 8) {
            _formData.update { currentState ->
                currentState.copy(
                    passwordError = "Mật khẩu phải có ít nhất 8 ký tự",
                )
            }
        } else if (_formData.value.confirmPassword != _formData.value.password) {
            _formData.update { currentState ->
                currentState.copy(
                    confirmPasswordError = "Mật khẩu không khớp",
                    confirmPassword = "",
                    passwordError = null
                )
            }
            _clearConfirmPasswordInput.value = true
        } else {
            _formData.update { currentState ->
                currentState.copy(
                    passwordError = null,
                    confirmPasswordError = null
                )
            }
        }

    }

    fun onClickBack() {
         viewModelScope.launch {
             _navigateBack.emit(Unit)
         }
    }

}