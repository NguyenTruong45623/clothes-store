package com.example.bt2.repository.online

data class ProductResponse(
    val code: Int,
    val `data`: ProductData,
    val error_message: String,
    val success_message: String
)