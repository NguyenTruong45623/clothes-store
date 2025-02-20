package com.example.bt2.feature.profilePage

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bt2.R
import com.example.bt2.repository.local.UserDataProfileStore
import com.example.bt2.repository.local.UserDataStore
import com.example.bt2.databinding.FragmentProfilePageBinding


class ProfilePageFragment : Fragment() {

    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var viewModel: ProfilePageViewModel
    private lateinit var userDataProfileStore: UserDataProfileStore
    private lateinit var userDataStore: UserDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page, container, false)
        binding.lifecycleOwner = this

        userDataProfileStore = UserDataProfileStore(requireContext())
        userDataStore = UserDataStore(requireContext())
        viewModel = ProfilePageViewModel(userDataStore,userDataProfileStore)
        binding.viewModel = viewModel

        binding.avatarImageView.setImageURI(Uri.parse(viewModel.userProfileModel.value.uriImage))

        return binding.root
    }


}