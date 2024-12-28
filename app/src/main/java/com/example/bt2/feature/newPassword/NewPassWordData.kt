package com.example.bt2.feature.newPassword

data class NewPassWordData(
    var password: String = "",
    var confirmPassword: String = "",
    var confirmPasswordError: String? = null,
    var passwordError: String? = null,
)
