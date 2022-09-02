package com.kud.hanzan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemHomeAlcoholBinding
import com.kud.hanzan.domain.model.Alcohol

class HomeAlcoholRVAdapter : RecyclerView.Adapter<HomeAlcoholRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemHomeAlcoholBinding
    private var alcoholList = ArrayList<Alcohol>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_home_alcohol, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alcoholList[position])
    }

    override fun getItemCount(): Int = alcoholList.size

    fun setData(data: List<Alcohol>){
        alcoholList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemHomeAlcoholBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isClicked = false
        fun bind(alcohol: Alcohol){
            binding.alcohol = alcohol
            binding.itemHomeAlcoholLayout.apply {
                when(alcohol.typeNum){
                    1 -> setBackgroundResource(R.drawable.bg_alcohol_type1)
                    3 -> setBackgroundResource(R.drawable.bg_alcohol_type3)
                    4 -> setBackgroundResource(R.drawable.bg_alcohol_type4)
                    else -> setBackgroundResource(R.drawable.bg_alcohol_type0)
                }
            }
            binding.itemHomeAlcoholLayout.setOnClickListener {
                isClicked = !isClicked
                binding.itemHomeAlcoholLayout.apply {
                    binding.itemHomeAlcoholSelectedLayout.visibility = if (isClicked) View.VISIBLE else View.GONE
                    binding.itemHomeAlcoholIv.alpha = if (isClicked) 0.5F else 1F
                }
            }
            alcohol.imgRes?.let { binding.itemHomeAlcoholIv.setImageResource(it) }
        }
    }

}