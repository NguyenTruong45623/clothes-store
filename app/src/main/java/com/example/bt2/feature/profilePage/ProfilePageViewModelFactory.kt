package com.example.bt2.feature.profilePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bt2.repository.local.dataStore.UserDataStore

class ProfileViewModelFactory(private val userDataStore: UserDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfilePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfilePageViewModel(userDataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}