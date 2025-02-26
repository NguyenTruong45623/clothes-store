package com.example.bt2.feature.favouritePage

import android.os.Bundle
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
import com.example.bt2.databinding.FragmentFavouritePageBinding
import com.example.bt2.feature.bottomNavigationBarContainer.BottomBarViewModel
import com.example.bt2.feature.bottomNavigationBarContainer.BottomBarViewModelFactory
import com.example.bt2.feature.homePage.adapter.SubCategoryAdapter
import com.example.bt2.repository.local.roomDataBase.AppDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouritePageFragment : Fragment() {

    private lateinit var binding : FragmentFavouritePageBinding
    private val db: AppDatabase by lazy { AppDatabase.getDatabase(requireContext()) }
    private val bottomBarViewModel: BottomBarViewModel by activityViewModels{BottomBarViewModelFactory(db)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.bottomBarViewModel = bottomBarViewModel


        binding.rvProductFavourite.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = ProductFavouriteAdapter(bottomBarViewModel)
        binding.rvProductFavourite.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    bottomBarViewModel.categories.collect { categoryList ->
                        binding.rvCategoriesFavourite.adapter = SubCategoryAdapter(categoryList)
                    }
                }
                launch {
                    bottomBarViewModel.clotheFavourite.collectLatest { item ->
                        val listFavourite = item.filter { it.isFavourite }
                        adapter.updateItems(listFavourite)
                    }
                }

            }
        }


        return binding.root
    }

}