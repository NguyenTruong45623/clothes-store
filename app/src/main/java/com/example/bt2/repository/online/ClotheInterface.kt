package com.example.bt2.repository.online

import retrofit2.http.GET

interface ClotheInterface {
    @GET("/clothes")
    suspend fun getProduct(): ProductResponse

    @GET("/category")
    suspend fun getCategory(): CategoryResponse
}