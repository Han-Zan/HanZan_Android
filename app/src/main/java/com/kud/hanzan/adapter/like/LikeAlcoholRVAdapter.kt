package com.kud.hanzan.adapter.like

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemLikeAlcoholBinding
import com.kud.hanzan.domain.model.LikeAlcohol

class LikeAlcoholRVAdapter : RecyclerView.Adapter<LikeAlcoholRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemLikeAlcoholBinding
    private var alcoholList = ArrayList<LikeAlcohol>()

    interface Listener{
        fun onDelete(drinkId: Long)
        fun onPost(drinkId: Long)
    }

    private lateinit var likeListener : Listener

    fun setListener(listener: Listener){
        likeListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_like_alcohol, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alcoholList[position])
    }

    override fun getItemCount(): Int = alcoholList.size

    fun setData(data: List<LikeAlcohol>){
        alcoholList = ArrayList(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLikeAlcoholBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alcohol: LikeAlcohol){
            binding.alcohol = alcohol
            alcohol.imgRes?.let {
                Glide.with(itemView)
                    .load(it)
                    .fitCenter()
                    .into(binding.itemLikeAlcoholIv)}
            binding.itemLikeAlcoholLikeCb.setOnClickListener {
                if (!binding.itemLikeAlcoholLikeCb.isChecked){
                    likeListener.onDelete(alcohol.id)
                    Log.e("okhttp delete test", alcohol.id.toString())
                }
                else likeListener.onPost(alcohol.id)
            }
        }
    }
}