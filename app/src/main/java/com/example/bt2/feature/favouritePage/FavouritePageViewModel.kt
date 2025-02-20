package com.example.bt2.feature.favouritePage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2.repository.online.ClotheData
import com.example.bt2.repository.online.RetrofitCallData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouritePageViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _products = MutableStateFlow<List<ClotheData>>(emptyList())
    val products = _products.asStateFlow()

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            try {
                val responseCategory = RetrofitCallData.categoryService.getCategory()
                val responseProduct = RetrofitCallData.productService.getProduct()
                Log.d("API_RESPONSE", "Categories: $responseCategory")
                _categories.value = responseCategory.data.category
                _products.value = responseProduct.data.clothes

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", "Error fetching categories: ${e.message}")
            }
        }

    }


}