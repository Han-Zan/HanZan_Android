package com.kud.hanzan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemDrinkBinding
import com.kud.hanzan.domain.model.Drink

class DrinkRVAdapter : RecyclerView.Adapter<DrinkRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemDrinkBinding
    private var alcoholList = ArrayList<Drink>()

    interface Listener{
        fun onDelete(drinkId: Long)
        fun onPost(drinkId: Long)
    }

    private lateinit var likeListener : Listener

    fun setListener(listener: Listener){
        likeListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_drink, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alcoholList[position])
    }

    override fun getItemCount(): Int = alcoholList.size

    fun setData(data: List<Drink>){
        alcoholList = ArrayList(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemDrinkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alcohol: Drink){
            binding.alcohol = alcohol
            binding.itemLikeAlcoholLikeCb.isChecked = alcohol.isPrefer
//            if (alcohol.imgRes == null)
//                binding.itemLikeAlcoholIv.setImageDrawable(null)
//            else alcohol.imgRes?.let {
//                Glide.with(itemView)
//                    .load(it)
//                    .fitCenter()
//                    .into(binding.itemLikeAlcoholIv)}
            binding.itemLikeAlcoholLikeCb.setOnClickListener {
                if (!binding.itemLikeAlcoholLikeCb.isChecked){
                    likeListener.onDelete(alcohol.id)
                    Log.e("okhttp delete test", alcohol.id.toString())
                }
                else likeListener.onPost(alcohol.id)
                alcohol.isPrefer = binding.itemLikeAlcoholLikeCb.isChecked
            }
        }
    }
}