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

    fun onPasswordChanged(newPassword: String) {
        _formState.value = formState.value.copy(password = newPassword)
    }

    fun onEmailChanged(newEmail: String) {
        _formState.value = formState.value.copy(email = newEmail)
    }

    fun onNameChanged(newName: String) {
        _formState.value = formState.value.copy(name = newName)
    }

    fun onClickSignUp(fragmentManager: FragmentManager) {
        val currentState = formState.value
        var updatedState = currentState.copy()


        if (currentState.password.length < 8) {
            updatedState = updatedState.copy(passwordError = "Mật khẩu phải có ít nhất 8 ký tự")

        } else {
            updatedState = updatedState.copy(passwordError = null)
            if(updatedState.emailError == null && updatedState.nameError == null) {
                fragmentManager.commit {
                    replace(R.id.fragment_container, YourProfileFragment())
                    addToBackStack(null)
                }
            }

        }

        updatedState = if (currentState.name.isEmpty()) {
            updatedState.copy(nameError = "Vui lòng nhập tên")
        } else {
            updatedState.copy(nameError = null)
        }

        if (currentState.email.isEmpty()) {
            updatedState = updatedState.copy(emailError = "Vui lòng nhập email")
        } else if (!currentState.email.isValidEmail()) {
            updatedState = updatedState.copy(emailError = "Email không hợp lệ")
        }else {
            updatedState = updatedState.copy(emailError = null)
        }

        _formState.value = updatedState

    }

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}