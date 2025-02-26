package com.example.bt2.feature.createAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bt2.R
import com.example.bt2.repository.local.dataStore.UserDataStore
import com.example.bt2.databinding.FragmentAccountCreateBinding


class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountCreateBinding
    private lateinit var userDataStore: UserDataStore
    private lateinit var viewModel: CreateAccountViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_create, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        userDataStore = UserDataStore(requireContext())
        viewModel = CreateAccountViewModel(userDataStore)

        binding.viewModel = viewModel




        return binding.root
    }
}