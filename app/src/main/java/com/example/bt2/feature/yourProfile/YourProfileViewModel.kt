package com.example.bt2.feature.yourProfile

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

class YourProfileViewModel : ViewModel() {

    fun onClickBackButton(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }


}