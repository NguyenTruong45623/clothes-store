package com.example.bt2.feature.yourProfile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentYourProfiileBinding
import com.example.bt2.feature.welcome.WelcomeFragmentDirections
import com.example.bt2.until.showPermissionRationale
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class YourProfileFragment : Fragment() {

    private lateinit var binding: FragmentYourProfiileBinding
    private val viewModel : YourProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_profiile, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.buttonSetAvatar.setOnClickListener {
            checkAndRequestPermissions()
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, gender)
        binding.autoSelectGender.setAdapter(arrayAdapter)

        val codeCountry = resources.getStringArray(R.array.codeCountry)
        val arrayAdapter2 = ArrayAdapter(requireContext(), R.layout.dropdown_item_code_country, codeCountry)
        binding.codeCountry.setAdapter(arrayAdapter2)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.formState.collectLatest { state ->
                    if (state.isClickBack) {
                        findNavController().popBackStack()
                    }
                    viewModel.onNavigationComplete()
                }
            }
        }

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

    private val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()
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
                showPermissionRationale(requireContext(),requestPermissionsLauncher)
            }
            else -> {
                requestPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

}