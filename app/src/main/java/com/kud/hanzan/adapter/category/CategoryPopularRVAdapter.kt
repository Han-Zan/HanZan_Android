package com.kud.hanzan.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemPopularKeywordBinding

class CategoryPopularRVAdapter : RecyclerView.Adapter<CategoryPopularRVAdapter.ViewHolder>() {
    private lateinit var binding : ItemPopularKeywordBinding
    private var keywordList = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_popular_keyword, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(keywordList[position], position)
    }

    override fun getItemCount(): Int = keywordList.size

    fun setData(data: List<String>){
        keywordList = data as ArrayList<String>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPopularKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: String, position: Int){
            binding.keyword = keyword
            if (position == 0)
                binding.itemKeywordBtn.setBackgroundResource(R.drawable.bg_yellow_button_test)
        }
    }
}