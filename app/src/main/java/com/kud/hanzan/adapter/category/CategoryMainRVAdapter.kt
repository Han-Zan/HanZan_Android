package com.kud.hanzan.adapter.category

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemMainCategoryBinding

class CategoryMainRVAdapter(data: List<String>) : RecyclerView.Adapter<CategoryMainRVAdapter.ViewHolder>() {
    private var selectedPos = -1
    private lateinit var binding: ItemMainCategoryBinding
    private val stringList = data as ArrayList<String>

    interface CategoryListener{
        fun onClick(position: Int)
    }

    private lateinit var categoryListener: CategoryListener

    fun setListener(listener: CategoryListener){
        categoryListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_category, parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (selectedPos != -1 && position != selectedPos){
            holder.setChecked(false)
        }

        holder.bind(stringList[position])
        holder.itemView.setOnClickListener {
            holder.setChecked(true)
            var beforePos = selectedPos
            selectedPos = holder.adapterPosition
            categoryListener.onClick(position)

            notifyItemChanged(beforePos)
        }
    }

    override fun getItemCount(): Int = stringList.size

    inner class ViewHolder(val binding: ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(type: String){
            binding.type = type
            binding.isChecked = false
        }
        fun setChecked(checked: Boolean){
            binding.isChecked = checked
        }
    }

}