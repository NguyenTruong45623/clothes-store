package com.example.bt2.feature.createAccount

import android.util.Patterns
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import com.example.bt2.R
import com.example.bt2.feature.yourProfile.YourProfileFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class CreateAccountViewModel : ViewModel() {

    private val _formState = MutableStateFlow(RegisterFormData())
    val formState: StateFlow<RegisterFormData> = _formState.asStateFlow()

    private val _navigateToProfile = MutableStateFlow(false)
    val navigateToProfile: StateFlow<Boolean> = _navigateToProfile.asStateFlow()

    fun onPasswordChanged(newPassword: String) {
        _formState.value = formState.value.copy(password = newPassword)
    }

    fun onEmailChanged(newEmail: String) {
        _formState.value = formState.value.copy(email = newEmail)
    }

    fun onNameChanged(newName: String) {
        _formState.value = formState.value.copy(name = newName)
    }

    fun onClickSignUp() {
        val currentState = formState.value
        var updatedState = currentState.copy()


        if (currentState.name.isEmpty()) {
            updatedState = updatedState.copy(nameError = "Vui lòng nhập tên")
        } else  if (currentState.email.isEmpty()) {
            updatedState = updatedState.copy(emailError = "Vui lòng nhập email")
            updatedState = updatedState.copy(nameError = null)
        } else if (!currentState.email.isValidEmail()) {
            updatedState = updatedState.copy(emailError = "Email không hợp lệ")
        } else if (currentState.password.length < 8) {
            updatedState = updatedState.copy(passwordError = "Mật khẩu phải có ít nhất 8 ký tự")
            updatedState = updatedState.copy(emailError = null)
        } else {
            updatedState = updatedState.copy(passwordError = null)

            _navigateToProfile.value = true
        }


        _formState.value = updatedState

    }

    fun onNavigationComplete() {
        _navigateToProfile.value = false
    }

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}