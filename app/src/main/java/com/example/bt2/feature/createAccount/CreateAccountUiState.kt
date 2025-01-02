package com.example.bt2.feature.createAccount

data class CreateAccountUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isClickToProfile: Boolean = false,
)