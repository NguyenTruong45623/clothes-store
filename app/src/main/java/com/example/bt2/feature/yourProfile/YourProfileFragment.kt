package com.example.bt2.feature.yourProfile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.bt2.R
import com.example.bt2.databinding.FragmentYourProfiileBinding
import com.example.bt2.repository.local.dataStore.UserDataStore
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class YourProfileFragment : Fragment() {

    private lateinit var binding: FragmentYourProfiileBinding
    private lateinit var viewModel : YourProfileViewModel
    private lateinit var dataStore: UserDataStore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_profiile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        dataStore = UserDataStore(requireContext())
        viewModel = YourProfileViewModel(dataStore)
        binding.viewModel = viewModel


        binding.buttonSetAvatar.setOnClickListener {
            checkAndRequestPermissions()
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, gender)
        binding.autoSelectGender.setAdapter(arrayAdapter)

        binding.autoSelectGender.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "Bạn chọn: $selectedItem", Toast.LENGTH_SHORT).show()
            viewModel.onGender(selectedItem)
        }


        binding.codeCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
                viewModel.onNumberCountry(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return binding.root
    }


    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                viewModel.onUriImage(it.toString())
                Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
                binding.avatarImageView.setImageURI(it)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Lỗi khi chọn ảnh!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Launcher để yêu cầu quyền truy cập thư viện
    private val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Quyền truy cập đã được cấp", Toast.LENGTH_SHORT).show()
            openGallery() // Mở thư viện ngay sau khi cấp quyền
        } else {
            Toast.makeText(requireContext(), "Quyền bị từ chối!", Toast.LENGTH_SHORT).show()
        }
    }

    // Kiểm tra và yêu cầu quyền
    private fun checkAndRequestPermissions() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {
                // Nếu quyền đã được cấp -> mở thư viện
                openGallery()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission) -> {
                // Nếu người dùng đã từ chối quyền trước đó -> Hiển thị hộp thoại giải thích
                showPermissionRationale(permission)
            }
            else -> {
                // Yêu cầu quyền nếu chưa có
                requestPermissionsLauncher.launch(permission)
            }
        }
    }

    // Mở thư viện ảnh
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    // Hiển thị hộp thoại giải thích quyền
    private fun showPermissionRationale(permission: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Yêu cầu quyền")
            .setMessage("Ứng dụng cần quyền truy cập ảnh để chọn ảnh đại diện. Vui lòng cấp quyền!")
            .setPositiveButton("Đồng ý") { _, _ ->
                requestPermissionsLauncher.launch(permission)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

}