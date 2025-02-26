package com.example.bt2.feature.homePage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bt2.R
import com.example.bt2.databinding.ItemProductBinding
import com.example.bt2.feature.bottomNavigationBarContainer.BottomBarViewModel
import com.example.bt2.feature.bottomNavigationBarContainer.FavouriteClotheModel


class ProductAdapter(private val bottomBarViewModel: BottomBarViewModel) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    private var cloths = emptyList<FavouriteClotheModel>()

    fun updateItems(newCloths: List<FavouriteClotheModel>) {
        this.cloths = newCloths
        notifyDataSetChanged()
    }

    inner class ProductHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favouriteClotheModel: FavouriteClotheModel) {
            binding.cloth = favouriteClotheModel
            binding.bottomBarViewModel = bottomBarViewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemProductBinding>(
            inflater,
            R.layout.item_product,
            parent,
            false
        )
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(cloths[position])
    }

    override fun getItemCount(): Int = cloths.size
}