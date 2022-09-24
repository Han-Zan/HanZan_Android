package com.kud.hanzan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemMapStoreBinding

class MapStoreRVAdapter : RecyclerView.Adapter<MapStoreRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemMapStoreBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_map_store, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 3

    inner class ViewHolder(val binding: ItemMapStoreBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}