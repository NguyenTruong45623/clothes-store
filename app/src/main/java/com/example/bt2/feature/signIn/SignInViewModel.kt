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
    private val _navigateToCreateAccount = MutableSharedFlow<Unit>()
    val navigateToCreateAccount: SharedFlow<Unit> = _navigateToCreateAccount.asSharedFlow()

    private val _navigateToVerifyCode = MutableSharedFlow<Unit>()
    val navigateToVerifyCode: SharedFlow<Unit> = _navigateToVerifyCode.asSharedFlow()

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
                    passwordError = null
                )
            }
        }
    }

    fun onClickForgotPassword() {
        viewModelScope.launch {
            _navigateToVerifyCode.emit(Unit)
        }
    }

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


    fun onClickSignUp() {
        viewModelScope.launch {
            _navigateToCreateAccount.emit(Unit)
        }
    }

}