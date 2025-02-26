package com.example.bt2.feature.productDetail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class ProductDetailViewModel : ViewModel() {
    fun onBackClick(view: View) {
        view.findNavController().popBackStack()
    }
}