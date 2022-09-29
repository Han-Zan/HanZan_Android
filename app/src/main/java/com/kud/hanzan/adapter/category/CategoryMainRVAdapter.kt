package com.kud.hanzan.adapter.category

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemMainCategoryBinding

class CategoryMainRVAdapter(private val data: List<String>) : RecyclerView.Adapter<CategoryMainRVAdapter.Adapter>() {
    private lateinit var binding: ItemMainCategoryBinding
    private val stringList = data as ArrayList<String>

    interface CategoryListener{
        fun onClick()
    }

    private lateinit var categoryListener: CategoryListener

    fun setListener(listener: CategoryListener){
        categoryListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_category, parent, false )
        return Adapter(binding)
    }

    override fun onBindViewHolder(holder: Adapter, position: Int) {
        holder.bind(stringList[position])
        holder.itemView.setOnClickListener { categoryListener.onClick() }
    }

    override fun getItemCount(): Int = stringList.size

    inner class Adapter(val binding: ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(type: String){
            binding.type = type
        }
    }

}