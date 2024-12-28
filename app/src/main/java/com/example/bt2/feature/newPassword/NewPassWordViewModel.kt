package com.example.bt2.feature.newPassword

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewPassWordViewModel : ViewModel() {
    private val _formData = MutableStateFlow(NewPassWordData())
    var formData = _formData.asStateFlow()

    private val _clearConfirmPasswordInput = MutableStateFlow(false) // Thêm StateFlow cho confirmPassword
    val clearConfirmPasswordInput = _clearConfirmPasswordInput.asStateFlow()

    fun onPasswordChanged(newPassword: String) {
        _formData.value = _formData.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        if (_clearConfirmPasswordInput.value) {
            _clearConfirmPasswordInput.value = false
        }
        _formData.value = _formData.value.copy(confirmPassword = newConfirmPassword)
    }

    fun onClickNewPassWord() {
        val currentState = _formData.value
        var updatedState = currentState.copy()

        if (currentState.password.length < 8) {
            updatedState = updatedState.copy(passwordError = "Mật khẩu phải có ít nhất 8 ký tự")
        } else if (currentState.confirmPassword != currentState.password) {
            updatedState = updatedState.copy(confirmPasswordError = "Mật khẩu không khớp")
            _clearConfirmPasswordInput.value = true
            updatedState = updatedState.copy(confirmPassword = "", passwordError = "")
        } else {
            updatedState = updatedState.copy(passwordError = null, confirmPasswordError = null)
        }

        _formData.value = updatedState
    }

    fun onClickBack(fragmentManager : FragmentManager) {
        fragmentManager.popBackStack()
    }
}