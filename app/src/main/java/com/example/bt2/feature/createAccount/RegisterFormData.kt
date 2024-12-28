package com.example.bt2.feature.createAccount

data class RegisterFormData(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var nameError: String? = null,
    var emailError: String? = null,
    var passwordError: String? = null,
)