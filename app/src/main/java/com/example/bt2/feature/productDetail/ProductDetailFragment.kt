package com.example.bt2.feature.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.bt2.R
import com.example.bt2.databinding.FragmentProductDetailBinding
import com.example.bt2.feature.bottomNavigationBarContainer.BottomBarViewModel
import com.example.bt2.feature.bottomNavigationBarContainer.BottomBarViewModelFactory
import com.example.bt2.repository.local.roomDataBase.AppDatabase

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val db: AppDatabase by lazy { AppDatabase.getDatabase(requireContext()) }
    private val viewModel : ProductDetailViewModel by viewModels()
    private val viewModelBottomBar: BottomBarViewModel by activityViewModels{ BottomBarViewModelFactory(db) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        binding.viewModelBottomBar = viewModelBottomBar
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}