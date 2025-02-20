package com.example.bt2.feature.signIn

import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.bt2.repository.local.UserDataProfileStore
import com.example.bt2.repository.local.UserDataStore
import com.example.bt2.repository.local.UserModel
import com.example.bt2.repository.local.UserProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(private val userDataStore: UserDataStore, private val userProfileDataStore: UserDataProfileStore) : ViewModel() {

    private val _formState = MutableStateFlow(SignInUiState())
    val formState: StateFlow<SignInUiState> = _formState.asStateFlow()

    private val _userModel = MutableStateFlow(UserModel("", "", ""))
    val userModel: StateFlow<UserModel> = _userModel

    private val _userProfileModel = MutableStateFlow(UserProfileModel("", "", ""))
    val userProfileModel: StateFlow<UserProfileModel> = _userProfileModel

    init {
        getDateUser()
        getDateUserProfile()
    }


    fun onPasswordChanged(newPassword: String) {
        _formState.update { it.copy(password = newPassword) }
    }

    fun onEmailChanged(newEmail: String) {
        _formState.update { it.copy(email = newEmail) }
    }

    fun onClickSignIn(view: View) {

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
            if (_formState.value.email == _userModel.value.email && _formState.value.password == _userModel.value.password) {
                _formState.update { currentState ->
                    currentState.copy(
                        passwordError = null,
                        emailError = null,
                        showError = ""
                    )
                }
                val action = SignInFragmentDirections.actionSignInFragmentToBottomNavigationBarFragment()
                view.findNavController().navigate(action)
            } else {
                _formState.update { currentState ->
                    currentState.copy(
                        passwordError = null,
                        emailError = null,
                        showError = "Tài khoản hoặc mật khẩu không đúng"
                    )
                }
            }

        }
    }

    private fun getDateUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.getUser().collect { user ->
                _userModel.value = user
            }
        }
    }

    fun onClickForgotPassword(view: View) {
        _formState.update { it.copy(isClickVerifyPassword = true) }
        val action = SignInFragmentDirections.actionSignInFragmentToVerifyCodeFragment()
        view.findNavController().navigate(action)
    }

    fun onClickSignUp(view: View) {
        val action = SignInFragmentDirections.actionSignInFragmentToCreateAccountFragment()
        view.findNavController().navigate(action)
    }

    private fun getDateUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            userProfileDataStore.getUserProfile().collect { user ->
                _userProfileModel.value = user
            }
        }
    }

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}