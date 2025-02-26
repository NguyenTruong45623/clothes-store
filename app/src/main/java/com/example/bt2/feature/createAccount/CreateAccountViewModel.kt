package com.example.bt2.feature.createAccount

import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.bt2.repository.local.dataStore.UserDataStore
import com.example.bt2.repository.local.dataStore.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateAccountViewModel (private val userDataStore: UserDataStore) : ViewModel() {

    private val _formState = MutableStateFlow(CreateAccountUiState())
    val formState: StateFlow<CreateAccountUiState> = _formState.asStateFlow()

    fun onPasswordChanged(newPassword: String) {
        _formState.update { it.copy(password = newPassword) }
    }

    fun onEmailChanged(newEmail: String) {
        _formState.update { it.copy(email = newEmail) }
    }

    fun onNameChanged(newName: String) {
        _formState.update { it.copy(name = newName) }
    }

    fun onClickSignUp(view: View) {
        try {
            if (_formState.value.name.isEmpty()) {
                _formState.update { currentState ->
                    currentState.copy(nameError = "Vui lòng nhập tên")
                }
            } else if (_formState.value.email.isEmpty()) {
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
                        emailError = null,
                        nameError = null
                    )
                }
            } else {
                _formState.update { currentState ->
                    currentState.copy(
                        passwordError = null,
                        emailError = null,
                        isClickToProfile = true,
                    )
                }
                savaData()
                val action = CreateAccountFragmentDirections.actionCreateAccountFragmentToYourProfileFragment()
                view.findNavController().navigate(action)
            }
        } catch (e: Exception) {
            // Log the exception or show an error message
            e.printStackTrace()
        }
    }

    private fun savaData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userDataStore.saveUser(
                    UserModel(
                        name = _formState.value.name,
                        email = _formState.value.email,
                        password = _formState.value.password,
                        uriImage = "",
                        phone = "",
                        gender = "",
                        country = ""
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}