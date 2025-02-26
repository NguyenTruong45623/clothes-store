package com.example.bt2.repository.online

data class ClotheData(
    val id: Int,
    val image: String,
    val name: String,
    val newest: Boolean,
    val popular: Boolean,
    val price: String,
    val rate: String,
    val sex: String
)