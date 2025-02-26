package com.example.bt2.repository.local.dataStore

data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val uriImage: String,
    val phone: String,
    val gender: String,
    val country: String
)
