package com.kud.hanzan.adapter.camera

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemCameraCombLayoutBinding
import com.kud.hanzan.domain.model.CombinationInfo

class CameraResultCombRVAdapter : RecyclerView.Adapter<CameraResultCombRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemCameraCombLayoutBinding
    // Todo : 임시로 Combination으로 해둠
    private var combList = mutableListOf<CombinationInfo>()

    private var selectedItemPosition: Int = -1
    private var prevItemPosition : Int = -1

    private lateinit var combListener: CustomListener

    override fun getItemId(position: Int): Long = position.toLong()

    interface CustomListener{
        fun onClick(combination: CombinationInfo, position: Int)
    }

    fun setCustomListener(listener: CustomListener){
        combListener = listener
    }

    fun getPosition() : Int = selectedItemPosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_camera_comb_layout, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == selectedItemPosition)
            holder.setBackground(true)
        else holder.setBackground(false)
        holder.bind(combList[position])
    }


    override fun getItemCount(): Int = combList.size

    fun setData(data: List<CombinationInfo>){
        combList.clear()
        combList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCameraCombLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(combination: CombinationInfo){
            binding.combination = combination
            binding.rank = adapterPosition + 1
            binding.rankingCombLayout.setOnClickListener {
                selectedItemPosition = adapterPosition
                if(prevItemPosition == -1)
                    prevItemPosition = selectedItemPosition
                else {
                    notifyItemChanged(prevItemPosition)
                    prevItemPosition = selectedItemPosition
                }
                notifyItemChanged(selectedItemPosition)
                combListener.onClick(combination, adapterPosition)
            }
        }

        fun setBackground(isSelected: Boolean){
            if (isSelected){
                binding.rankingCombLayout.setBackgroundColor(Color.parseColor("#33FFAA11"))
            } else{
                binding.rankingCombLayout.setBackgroundColor(Color.TRANSPARENT)

            }
        }
    }
}