package com.example.bt2.feature.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2.repository.online.ClotheData
import com.example.bt2.repository.online.RetrofitCallData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _clothes = MutableStateFlow<List<ClotheData>>(emptyList())
    val clothes = _clothes.asStateFlow()

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        fetch()
    }

     private fun fetch() {
         viewModelScope.launch {
             try {
                 val responseCategory = RetrofitCallData.categoryService.getCategory()
                 val responseProduct = RetrofitCallData.productService.getProduct()
                 _categories.value = responseCategory.data.category
                 _clothes.value = responseProduct.data.clothes
                 val isLoading = _clothes.value.isEmpty() && _categories.value.isEmpty()
                 _homeUiState.value = _homeUiState.value.copy(isLoading = isLoading)
             } catch (e: Exception) {
                 e.printStackTrace()
             }
         }

     }

}