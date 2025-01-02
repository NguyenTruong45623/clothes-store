package com.example.bt2.feature.yourProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class YourProfileViewModel : ViewModel() {

    private val _formState = MutableStateFlow(YourProfileUiState())
    val formState: StateFlow<YourProfileUiState> = _formState.asStateFlow()

    fun onClickBackButton() {
       _formState.update { it.copy(isClickBack = true) }
    }

    fun onNavigationComplete() {
        _formState.update { it.copy(isClickBack = false) }
    }

}