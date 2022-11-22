package com.kud.hanzan.adapter.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemStoreMenuImageBinding

class StoreMenuImageRVAdapter : RecyclerView.Adapter<StoreMenuImageRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemStoreMenuImageBinding

    private var imgList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_store_menu_image, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    override fun getItemCount(): Int = imgList.size

    fun setData(data: List<String>){
        imgList.clear()
        imgList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStoreMenuImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String){
            binding.imgUrl = url
        }
    }
}