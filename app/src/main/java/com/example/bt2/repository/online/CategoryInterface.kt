package com.example.bt2.repository.online

import retrofit2.http.GET

interface CategoryInterface {
    @GET("/category")
    suspend fun getCategory(): CategoryResponse
}