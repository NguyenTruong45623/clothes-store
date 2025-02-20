package com.example.bt2.feature.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bt2.R
import com.example.bt2.databinding.FragmentHomePageBinding
import com.example.bt2.feature.homePage.adapter.BannerAdapter
import com.example.bt2.feature.homePage.adapter.ProductAdapter
import com.example.bt2.feature.homePage.adapter.SubCategoryAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    val viewModel: HomePageViewModel by viewModels()
    private val images = listOf(
        R.drawable.banner_home,
        R.drawable.banner_home,
        R.drawable.banner_home,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewmodel = viewModel

        binding.vpBannerHome.adapter = BannerAdapter(images)
        TabLayoutMediator(binding.intoTabLayout, binding.vpBannerHome) { tab, position ->}.attach()

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProduct.layoutManager = layoutManager

        val adapter = ProductAdapter()
        binding.rvProduct.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categories.collect { categoryList ->
                        binding.rvCategories.adapter = SubCategoryAdapter(categoryList)
                    }
                }

                launch {
                    viewModel.clothes.collect { clothe ->
                        adapter.updateItems(clothe)
                    }
                }
            }
        }




        return binding.root
    }

}