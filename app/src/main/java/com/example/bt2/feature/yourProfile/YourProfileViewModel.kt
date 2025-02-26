package com.example.bt2.feature.yourProfile

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.bt2.repository.local.dataStore.UserDataStore
import com.example.bt2.repository.local.dataStore.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class YourProfileViewModel(private val userDataStore: UserDataStore) : ViewModel() {

    private val _formState = MutableStateFlow(YourProfileUiState())
    val formState: StateFlow<YourProfileUiState> = _formState.asStateFlow()

    private val _userModel = MutableStateFlow(UserModel("", "", "","","","",""))
    val userModel: StateFlow<UserModel> = _userModel

    init {
        getDateUser()
    }

    fun onUriImage(newUriImage: String) {
        _formState.update { it.copy(uriImage = newUriImage) }
    }

    fun onNumberCountry(newNumberCountry: String) {
        _formState.update { it.copy(country = newNumberCountry) }
    }

    fun onNumberPhone(newPhone: String) {
        _formState.update { it.copy(phone = newPhone) }
    }

    fun onGender(newGender: String) {
        _formState.update { it.copy(gender = newGender) }
    }

    fun onClickBackButton(view : View) {
        view.findNavController().popBackStack()
    }

    fun onClickSaveProfile(view : View) {
        updateUserProfile(
            uriImage = _formState.value.uriImage,
            phone = _formState.value.phone,
            gender = _formState.value.gender,
            country = _formState.value.country
        )
        Toast.makeText(view.context, userModel.value.uriImage, Toast.LENGTH_SHORT).show()
        val action = YourProfileFragmentDirections.actionYourProfileFragmentToSignInFragment()
        view.findNavController().navigate(action)
    }



    private fun getDateUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.getUser().collect { user ->
                _userModel.value = user
            }
        }
    }


    private fun updateUserProfile(uriImage: String, phone: String, gender: String,country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = _userModel.value

                val updatedUser = currentUser.copy(
                    uriImage = uriImage,
                    phone = phone,
                    gender = gender,
                    country = country
                )
                _userModel.value = updatedUser
                userDataStore.saveUser(_userModel.value)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}