package com.example.bt2.feature.yourProfile

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class YourProfileViewModel : ViewModel() {

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack: StateFlow<Boolean> = _navigateBack.asStateFlow()

    fun onClickBackButton() {
        _navigateBack.value = true
    }

    fun onNavigationComplete() {
        _navigateBack.value = false
    }


}