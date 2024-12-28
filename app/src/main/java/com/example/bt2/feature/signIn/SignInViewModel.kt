package com.example.bt2.feature.signIn

import android.util.Patterns
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import com.example.bt2.R
import com.example.bt2.feature.createAccount.CreateAccountFragment
import com.example.bt2.feature.verifyCode.VerifyCodeFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel : ViewModel() {
    private val _formState = MutableStateFlow(SignInFormData())
    val formState: StateFlow<SignInFormData> = _formState.asStateFlow()

    fun onPasswordChanged(newPassword: String) {
        _formState.value = formState.value.copy(password = newPassword)
    }

    fun onEmailChanged(newEmail: String) {
        _formState.value = formState.value.copy(email = newEmail)
    }

    fun onClickSignIn() {

        val currentState = formState.value
        var updatedState = currentState.copy()

        updatedState = if (currentState.email.isEmpty()) {
            updatedState.copy(emailError = "Vui lòng nhập email")
        } else if(!currentState.email.isValidEmail()) {
            updatedState.copy(emailError = "Email không hợp lệ")
        } else {
            updatedState.copy(emailError = null)
        }

        updatedState = if (currentState.password.length < 8) {
            updatedState.copy(passwordError = "Vui lòng nhập mật khẩu")
        } else {
            updatedState.copy(passwordError = null)
        }

        _formState.value = updatedState
    }

    fun onClickForgotPassword(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace(R.id.fragment_container, VerifyCodeFragment())
            addToBackStack(null)
        }
    }

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


    fun onClickSignUp(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            replace(R.id.fragment_container, CreateAccountFragment())
            addToBackStack(null)
        }
    }
}