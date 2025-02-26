package com.example.bt2.feature.profilePage

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2.repository.local.dataStore.UserDataStore
import com.example.bt2.repository.local.dataStore.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfilePageViewModel(private val userDataStore: UserDataStore) : ViewModel() {
    val listGender = listOf("Nam", "Nữ", "Khác")

    private val _userModel = MutableStateFlow(UserModel("", "", "","","","",""))
    val userModel: StateFlow<UserModel> get() = _userModel

    private val _updateStatus = MutableStateFlow<UpdateStatus>(UpdateStatus.Idle)
    val updateStatus: StateFlow<UpdateStatus> = _updateStatus


    init {
        getDateUser()
    }

    fun onUriImageChanged(newUriImage: String) { _userModel.update { it.copy(uriImage = newUriImage) } }

    fun onNumberCountryChanged(newNumberCountry: String) { _userModel.update { it.copy(country = newNumberCountry) } }

    fun onNumberPhoneChanged(newPhone: String) { _userModel.update { it.copy(phone = newPhone) } }

    fun onGenderChanged(newGender: String) { _userModel.update { it.copy(gender = newGender) } }

    fun onPasswordChanged(newPassword: String) { _userModel.update { it.copy(password = newPassword) } }

    fun onEmailChanged(newEmail: String) { _userModel.update { it.copy(email = newEmail) } }

    fun onNameChanged(newName: String) { _userModel.update { it.copy(name = newName) } }

    private fun getDateUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.getUser().collect {user ->
                _userModel.value = user
                Log.d("ProfileViewModel", "Loaded user: $user")
            }
        }
    }

    fun updateUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userModel.update { it.copy(
                    name = _userModel.value.name,
                    email = _userModel.value.email,
                    uriImage = _userModel.value.uriImage,
                    phone = _userModel.value.phone,
                    gender = _userModel.value.gender,
                    country = _userModel.value.country
                ) }

                userDataStore.saveUser(_userModel.value)
                _updateStatus.value = UpdateStatus.Success

            } catch (e: Exception) {
                e.printStackTrace()
                _updateStatus.value = UpdateStatus.Error("Đã xảy ra lỗi khi cập nhật: ${e.message}")
            }
        }
    }

    fun resetUpdateStatus() {
        _updateStatus.value = UpdateStatus.Idle
    }



}