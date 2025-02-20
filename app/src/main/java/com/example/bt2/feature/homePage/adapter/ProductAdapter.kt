package com.example.bt2.feature.homePage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bt2.R
import com.example.bt2.databinding.ItemProductBinding
import com.example.bt2.repository.online.ClotheData


class ProductAdapter() :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    private var cloths = emptyList<ClotheData>()

    fun updateItems(newCloths: List<ClotheData>) {
        this.cloths = newCloths
        notifyDataSetChanged()
    }

    class ProductHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clotheData: ClotheData) {
            binding.cloth = clotheData
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