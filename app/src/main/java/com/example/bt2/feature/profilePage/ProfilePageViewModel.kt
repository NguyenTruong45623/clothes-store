package com.example.bt2.feature.profilePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2.repository.local.UserDataProfileStore
import com.example.bt2.repository.local.UserDataStore
import com.example.bt2.repository.local.UserModel
import com.example.bt2.repository.local.UserProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfilePageViewModel(private val userDataStore: UserDataStore, private val userProfileDataStore: UserDataProfileStore) : ViewModel() {
    private val _userModel = MutableStateFlow(UserModel("", "", ""))
    val userModel: StateFlow<UserModel> = _userModel
    private val _userProfileModel = MutableStateFlow(UserProfileModel("", "", ""))
    val userProfileModel: StateFlow<UserProfileModel> = _userProfileModel


    init {
        getDateUser()
        getDateUserProfile()
    }

    private fun getDateUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.getUser().collect { user ->
                _userModel.value = user
            }
        }
    }

    private fun getDateUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            userProfileDataStore.getUserProfile().collect { user ->
                _userProfileModel.value = user
            }
        }
    }

}