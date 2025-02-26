package com.example.bt2.feature.bottomNavigationBarContainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bt2.R
import com.example.bt2.databinding.FragmentBottomNavigationBarBinding

class BottomBarFragment : Fragment() {

    private lateinit var binding: FragmentBottomNavigationBarBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_navigation_bar, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        val visibleDestinations = setOf(
            R.id.homePage,
            R.id.favouritePage,
            R.id.profilePage
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible = destination.id in visibleDestinations
        }



        return binding.root
    }


}