package com.example.bt2.feature.yourProfile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bt2.R
import com.example.bt2.databinding.FragmentYourProfiileBinding
import kotlinx.coroutines.launch
import java.io.File

class YourProfileFragment : Fragment() {

    private lateinit var binding: FragmentYourProfiileBinding
    private val viewModel : YourProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_profiile, container, false)

        binding.viewModel = viewModel
        binding.fm = parentFragmentManager

        binding.buttonSetAvatar.setOnClickListener {
            checkAndRequestPermissions()
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, gender)
        binding.autoSelectGender.setAdapter(arrayAdapter)

        val codeCountry = resources.getStringArray(R.array.codeCountry)
        val arrayAdapter2 = ArrayAdapter(requireContext(), R.layout.dropdown_item_code_country, codeCountry)
        binding.codeCountry.setAdapter(arrayAdapter2)

        return binding.root
    }


    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try{
            binding.avatarImageView.setImageURI(galleryUri)
        }catch(e:Exception){
            e.printStackTrace()
        }

    }

    private val requestMultiplePermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Đã cấp tất cả các quyền", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Một hoặc nhiều quyền chưa được cấp", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAndRequestPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireContext(), "Tất cả các quyền đã được cấp", Toast.LENGTH_SHORT).show()
                galleryLauncher.launch("image/*")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showPermissionRationale(requireContext())
            }
            else -> {
                requestMultiplePermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }


    private fun showPermissionRationale(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Yêu cầu quyền")
            .setMessage("Ứng dụng cần quyền này để hoạt động đúng. Vui lòng cấp quyền để tiếp tục.")
            .setPositiveButton("OK") { _, _ ->
                requestMultiplePermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}