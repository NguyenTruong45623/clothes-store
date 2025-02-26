
package com.example.bt2.feature.profilePage

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bt2.R
import com.example.bt2.repository.local.dataStore.UserDataStore
import com.example.bt2.databinding.FragmentProfilePageBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfilePageFragment : Fragment() {
    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var viewModel: ProfilePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val userDataStore = UserDataStore(requireContext())
        val factory = ProfileViewModelFactory(userDataStore)
        viewModel = ViewModelProvider(this, factory)[ProfilePageViewModel::class.java]
        binding.viewModel = viewModel

        binding.buttonSetAvatar.setOnClickListener {
            openGallery()
        }

        // Thiết lập adapter cho gender và country
        val listGender = resources.getStringArray(R.array.gender)
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, listGender)
        binding.autoSelectGender.setAdapter(genderAdapter)

        binding.autoSelectGender.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "Bạn chọn: $selectedItem", Toast.LENGTH_SHORT).show()
            viewModel.onGenderChanged(selectedItem)
        }


        val listCountry = resources.getStringArray(R.array.codeCountry)
        val countryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listCountry)
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.codeCountry.adapter = countryAdapter

        // Đặt giá trị ban đầu cho Spinner từ viewModel.userModel
        val initialCountryPosition = countryAdapter.getPosition(viewModel.userModel.value.country)
        if (initialCountryPosition >= 0) {
            binding.codeCountry.setSelection(initialCountryPosition)
        }

        // Listener để cập nhật khi người dùng chọn giá trị mới
        binding.codeCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Log.d("ProfilePage", "Spinner selected: $selectedItem")
                viewModel.onNumberCountryChanged(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Không cần xử lý
            }
        }

        // Đồng bộ UI với userModel
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.userModel.collectLatest { user ->
                        Log.d("ProfilePage", "UserModel updated: $user")
                        val positionGender = genderAdapter.getPosition(user.gender)
                        if (positionGender >= 0 && binding.autoSelectGender.text.toString() != user.gender) {
                            binding.autoSelectGender.setText(genderAdapter.getItem(positionGender).toString(), false)
                        }

                        val countryPosition = countryAdapter.getPosition(user.country)
                        if (countryPosition >= 0 && binding.codeCountry.selectedItemPosition != countryPosition) {
                            binding.codeCountry.setSelection(countryPosition)
                        }
                    }
                }

                launch {
                    viewModel.updateStatus.collect { status ->
                        Log.d("ProfilePage", "UpdateStatus: $status")
                        when (status) {
                            UpdateStatus.Success -> {
                                showAlertDialog("Cập nhật thông tin thành công!")
                                viewModel.resetUpdateStatus()
                            }
                            is UpdateStatus.Error -> {
                                showAlertDialog(status.message)
                                viewModel.resetUpdateStatus()
                            }
                            UpdateStatus.Idle -> {}
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                viewModel.onUriImageChanged(it.toString())
                Toast.makeText(requireContext(), "Avatar updated", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Lỗi khi chọn ảnh!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Thông báo")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .show()
    }
}