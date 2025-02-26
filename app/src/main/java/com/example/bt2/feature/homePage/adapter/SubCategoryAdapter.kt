package com.example.bt2.feature.homePage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bt2.R

class SubCategoryAdapter(private val categorys : List<String>) :
    RecyclerView.Adapter<SubCategoryAdapter.SubCategoryHolder>(){
    class SubCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView = itemView.findViewById(R.id.itemSubCategory)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sub_category,parent,false)
        return SubCategoryHolder(view)
    }

    override fun onBindViewHolder(holder: SubCategoryHolder, position: Int) {
        val category = categorys[position]
        holder.category.text = category
    }

    override fun getItemCount(): Int = categorys.size

}