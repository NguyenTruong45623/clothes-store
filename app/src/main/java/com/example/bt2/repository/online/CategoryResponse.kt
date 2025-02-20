package com.example.bt2.repository.online

data class CategoryResponse(
    val error_message: String,
    val success_message: String,
    val code: Int,
    val data: CategoryData
)


