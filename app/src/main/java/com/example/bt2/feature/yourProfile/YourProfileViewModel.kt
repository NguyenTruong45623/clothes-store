package com.example.bt2.feature.yourProfile

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.bt2.repository.local.UserDataProfileStore
import com.example.bt2.repository.local.UserProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class YourProfileViewModel(private val userProfileDataStore: UserDataProfileStore) : ViewModel() {
    val arrCountryPhone = arrayOf("+1", "+84", "+18", "+14", "+43", "+13", "+15")

    private val _formState = MutableStateFlow(YourProfileUiState())
    val formState: StateFlow<YourProfileUiState> = _formState.asStateFlow()

    fun onUriImage(newUriImage: String) {
        _formState.update { it.copy(uriImage = newUriImage) }
    }

    fun onNumberCountry(newNumberCountry: String) {
        _formState.update { it.copy(phone = newNumberCountry) }
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
        savaData()
        val action = YourProfileFragmentDirections.actionYourProfileFragmentToSignInFragment()
        view.findNavController().navigate(action)
    }

    private fun savaData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userProfileDataStore.saveUserProfile(
                    UserProfileModel(
                        uriImage = _formState.value.uriImage,
                        phone = _formState.value.phone,
                        gender = _formState.value.gender
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}