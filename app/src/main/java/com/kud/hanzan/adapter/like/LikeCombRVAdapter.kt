package com.kud.hanzan.adapter.like

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemLikeCombBinding
import com.kud.hanzan.domain.model.Combination

class LikeCombRVAdapter : RecyclerView.Adapter<LikeCombRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemLikeCombBinding
    private var combList = ArrayList<Combination>()

    override fun getItemCount(): Int = combList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_like_comb, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(combList[position])
    }

    fun setData(data: List<Combination>){
        combList.clear()
        combList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLikeCombBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(combination: Combination){
            binding.combination = combination
        }
    }
}