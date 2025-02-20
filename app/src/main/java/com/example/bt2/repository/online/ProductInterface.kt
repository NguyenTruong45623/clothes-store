package com.example.bt2.repository.online

import retrofit2.http.GET

interface ProductInterface {
    @GET("/clothes")
    suspend fun getProduct(): ProductResponse

}