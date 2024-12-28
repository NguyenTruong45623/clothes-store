package com.example.bt2.feature.verifyCode

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import com.example.bt2.R
import com.example.bt2.feature.newPassword.NewPasswordFragment

class VerifyCodeViewModel : ViewModel() {

    fun onClickVerifyButton(fragmentManager: FragmentManager) {
            fragmentManager.commit {
                replace(R.id.fragment_container, NewPasswordFragment())
                addToBackStack(null)
            }
    }

    fun onClickBackButton(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }
}