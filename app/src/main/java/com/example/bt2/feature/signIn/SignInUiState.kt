package com.example.bt2.feature.signIn

data class SignInUiState (
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isClickSignUp: Boolean = false,
    val isClickVerifyPassword: Boolean = false,
    val showError: String = "ZXczxc"
)