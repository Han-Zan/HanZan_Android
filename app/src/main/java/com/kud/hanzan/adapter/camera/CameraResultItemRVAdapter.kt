package com.kud.hanzan.adapter.camera

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemCameraResultBinding

class CameraResultItemRVAdapter : RecyclerView.Adapter<CameraResultItemRVAdapter.ViewHolder>() {
    private var itemList = ArrayList<String>()
    private lateinit var binding: ItemCameraResultBinding

    interface ItemListener{
        fun onDelete(item: String, position: Int)
    }

    private lateinit var itemListener: ItemListener

    fun setCustomListener(listener: ItemListener){
        itemListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_camera_result, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun setData(data: List<String>){
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(val binding : ItemCameraResultBinding) :  RecyclerView.ViewHolder(binding.root){
        fun bind(item: String){
            binding.item = item
            binding.itemCameraResultRemoveBtn.setOnClickListener {
                itemListener.onDelete(item, adapterPosition)

            }
        }
    }
}