package com.kud.hanzan.adapter.like

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemLikeAlcoholBinding
import com.kud.hanzan.domain.model.Alcohol

class LikeAlcoholRVAdapter : RecyclerView.Adapter<LikeAlcoholRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemLikeAlcoholBinding
    private var alcoholList = ArrayList<Alcohol>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_like_alcohol, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alcoholList[position])
    }

    override fun getItemCount(): Int = alcoholList.size

    fun setData(data: List<Alcohol>){
        alcoholList = ArrayList(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLikeAlcoholBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alcohol: Alcohol){
            binding.alcohol = alcohol
            alcohol.imgRes?.let { binding.itemLikeAlcoholIv.setImageResource(it) }
        }
    }
}