package com.example.bt2.feature.homePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bt2.R
import com.example.bt2.databinding.FragmentHomePageBinding
import com.example.bt2.feature.bottomNavigationBarContainer.BottomBarViewModel
import com.example.bt2.feature.bottomNavigationBarContainer.BottomBarViewModelFactory
import com.example.bt2.feature.homePage.adapter.BannerAdapter
import com.example.bt2.feature.homePage.adapter.ProductAdapter
import com.example.bt2.feature.homePage.adapter.SubCategoryAdapter
import com.example.bt2.repository.local.roomDataBase.AppDatabase
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val db: AppDatabase by lazy { AppDatabase.getDatabase(requireContext()) }
    private val bottomBarViewModel: BottomBarViewModel by activityViewModels{ BottomBarViewModelFactory(db) }


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
        binding.bottomBarViewModel = bottomBarViewModel


        binding.vpBannerHome.adapter = BannerAdapter(images)
        TabLayoutMediator(binding.intoTabLayout, binding.vpBannerHome) { _, _ ->}.attach()

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProduct.layoutManager = layoutManager

        val adapter = ProductAdapter(bottomBarViewModel)
        binding.rvProduct.adapter = adapter




        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    bottomBarViewModel.categories.collect { categoryList ->
                        binding.rvCategories.adapter = SubCategoryAdapter(categoryList)
                    }
                }

                launch {
                    bottomBarViewModel.clotheFavourite.collect { clothe ->
                        Log.d("HomePageFragment", "Fetched clothes: $clothe")
                        adapter.updateItems(clothe)
                    }
                }
            }
        }



        return binding.root
    }

}