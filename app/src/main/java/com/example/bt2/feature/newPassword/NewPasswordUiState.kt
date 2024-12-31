package com.example.bt2.feature.newPassword

data class NewPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val passwordError: String? = null,
)
