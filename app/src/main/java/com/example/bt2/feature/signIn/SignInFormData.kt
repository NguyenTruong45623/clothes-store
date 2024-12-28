package com.example.bt2.feature.signIn

data class SignInFormData (
    var email: String = "",
    var password: String = "",
    var emailError: String? = null,
    var passwordError: String? = null,
)