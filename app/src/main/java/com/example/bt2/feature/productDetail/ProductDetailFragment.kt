package com.example.bt2.feature.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bt2.R
import com.example.bt2.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)

        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

}