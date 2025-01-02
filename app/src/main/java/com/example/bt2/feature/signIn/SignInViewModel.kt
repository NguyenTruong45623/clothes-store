package com.example.bt2.feature.signIn

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val _formState = MutableStateFlow(SignInUiState())
    val formState: StateFlow<SignInUiState> = _formState.asStateFlow()

    fun onPasswordChanged(newPassword: String) {
        _formState.update { it.copy(password = newPassword) }
    }

    fun onEmailChanged(newEmail: String) {
        _formState.update { it.copy(email = newEmail) }
    }

    fun onClickSignIn() {


        if (_formState.value.email.isEmpty()) {
            _formState.update { currentState ->
                currentState.copy(emailError = "Vui lòng nhập email")
            }
        } else if(!_formState.value.email.isValidEmail()) {
            _formState.update { currentState ->
                currentState.copy(emailError = "Email không hợp lệ")
            }
        } else if (_formState.value.password.length < 8) {
            _formState.update { currentState ->
                currentState.copy(
                    passwordError = "Vui lòng nhập mật khẩu",
                    emailError = null
                )
            }
        } else {
            _formState.update { currentState ->
                currentState.copy(
                    passwordError = null,
                    emailError = null
                )
            }
        }
    }

    fun onClickForgotPassword() {
        _formState.update { it.copy(isClickVerifyPassword = true) }
    }

    fun onClickSignUp() {
        _formState.update { it.copy(isClickSignUp = true) }
    }

    fun onNavigationComplete() {
        _formState.update { it.copy(isClickVerifyPassword = false) }
        _formState.update { it.copy(isClickSignUp = false) }
    }

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}