package com.example.bt2.repository.online

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCallData {
    private const val CATEGORY_URL = "https://demo6217650.mockable.io/"


    private val retrofitService: Retrofit = Retrofit.Builder()
            .baseUrl(CATEGORY_URL)
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val clothService : ClotheInterface = retrofitService.create(ClotheInterface::class.java)
}