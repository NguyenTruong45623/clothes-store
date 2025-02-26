package com.example.bt2.feature.bottomNavigationBarContainer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bt2.repository.local.roomDataBase.AppDatabase

class BottomBarViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomBarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BottomBarViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}