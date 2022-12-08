package com.kud.hanzan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemDrinkCombLayoutBinding
import com.kud.hanzan.domain.model.DrinkComb

class DrinkFoodRVAdapter : RecyclerView.Adapter<DrinkFoodRVAdapter.ViewHolder>() {
    private var foodList = mutableListOf<DrinkComb>()
    private lateinit var binding: ItemDrinkCombLayoutBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_drink_comb_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    fun setData(data : List<DrinkComb>){
        foodList.clear()
        Log.e("okhttp food data", data.toString() )
        foodList.addAll(data)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemDrinkCombLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: DrinkComb){
            binding.food = food
        }
    }

}