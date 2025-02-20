package com.example.bt2.feature.favouritePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bt2.R
import com.example.bt2.databinding.FragmentFavouritePageBinding
import com.example.bt2.feature.homePage.adapter.SubCategoryAdapter
import kotlinx.coroutines.launch

class FavouritePageFragment : Fragment() {

    private lateinit var binding : FragmentFavouritePageBinding
    private val viewModel: FavouritePageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProductFavourite.layoutManager = gridLayoutManager

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categories.collect { categoryList ->
                        binding.rvCategoriesFavourite.adapter = SubCategoryAdapter(categoryList)
                    }
                }

            }
        }


        return binding.root
    }

}