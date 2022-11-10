package com.kud.hanzan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemFoodBinding
import com.kud.hanzan.domain.model.Drink
import com.kud.hanzan.domain.model.Food

class FoodRVAdapter : RecyclerView.Adapter<FoodRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemFoodBinding
    private var foodList = ArrayList<Food>()

    inner class ViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food){
            binding.food = food
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_food, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(data: List<Food>){
        foodList.addAll(data)
        Log.e("Hello", "Hello")
        Log.e("Hello", foodList.toString())
        notifyDataSetChanged()
    }
}