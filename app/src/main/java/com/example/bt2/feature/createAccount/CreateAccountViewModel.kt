package com.example.bt2.feature.createAccount

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


class CreateAccountViewModel : ViewModel() {

    private val _formState = MutableStateFlow(CreateAccountUiState())
    val formState: StateFlow<CreateAccountUiState> = _formState.asStateFlow()

    private val _navigateToProfile = MutableSharedFlow<Unit>()
    val navigateToProfile: SharedFlow<Unit> = _navigateToProfile.asSharedFlow()

    fun onPasswordChanged(newPassword: String) {
        _formState.update { it.copy(password = newPassword) }
    }

    fun onEmailChanged(newEmail: String) {
        _formState.update { it.copy(email = newEmail) }
    }

    fun onNameChanged(newName: String) {
        _formState.update { it.copy(name = newName) }
    }

    fun onClickSignUp() {

        if (_formState.value.name.isEmpty()) {
            _formState.update { currentState ->
                currentState.copy(nameError = "Vui lòng nhập tên")
            }
        } else  if (_formState.value.email.isEmpty()) {
            _formState.update { currentState ->
                currentState.copy(
                    emailError = "Vui lòng nhập email",
                    nameError = null
                )
            }
        } else if (!_formState.value.email.isValidEmail()) {
            _formState.update { currentState ->
                currentState.copy(
                    emailError = "Email không hợp lệ",
                    nameError = null
                )
            }
        } else if (_formState.value.password.length < 8) {
            _formState.update { currentState ->
                currentState.copy(
                    passwordError = "Mật khẩu phải có ít nhất 8 ký tự",
                    emailError = null
                )
            }
        } else {
            _formState.update { currentState ->
                currentState.copy(
                    passwordError = null
                )
            }
            viewModelScope.launch {
                _navigateToProfile.emit(Unit)
            }
        }
    }


    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}