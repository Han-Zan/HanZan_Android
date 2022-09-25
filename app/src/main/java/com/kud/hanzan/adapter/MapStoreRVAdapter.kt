package com.kud.hanzan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemMapStoreBinding
import com.kud.hanzan.domain.model.Store

class MapStoreRVAdapter : RecyclerView.Adapter<MapStoreRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemMapStoreBinding
    private var storeList = ArrayList<Store>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_map_store, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storeList[position])
    }

    override fun getItemCount(): Int = storeList.size

    fun setData(data: List<Store>){
        storeList = data as ArrayList<Store> /* = java.util.ArrayList<com.kud.hanzan.domain.model.Store> */
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemMapStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store){
            binding.store = store
        }
    }
}