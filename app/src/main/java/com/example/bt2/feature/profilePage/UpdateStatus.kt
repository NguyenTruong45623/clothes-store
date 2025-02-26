package com.example.bt2.feature.profilePage

sealed class UpdateStatus {
    object Idle : UpdateStatus()
    object Success : UpdateStatus()
    data class Error(val message: String) : UpdateStatus()
}

