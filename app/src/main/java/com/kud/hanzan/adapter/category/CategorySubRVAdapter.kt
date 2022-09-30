package com.kud.hanzan.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemSubCategoryBinding
import com.kud.hanzan.domain.model.Category

class CategorySubRVAdapter : RecyclerView.Adapter<CategorySubRVAdapter.ViewHolder>() {
    private var categoryList = ArrayList<Category>()
    private lateinit var binding : ItemSubCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_sub_category, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size

    fun setData(data: ArrayList<Category>){
        categoryList = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSubCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category){
            binding.category = category
        }
    }


}