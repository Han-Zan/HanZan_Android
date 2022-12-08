package com.kud.hanzan.adapter.camera

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.widget.PopupWindowCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemCameraCombLayoutBinding
import com.kud.hanzan.domain.model.RecommendItem

class CameraResultCombRVAdapter : RecyclerView.Adapter<CameraResultCombRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemCameraCombLayoutBinding
    private var combList = mutableListOf<RecommendItem>()

    private lateinit var customView : View

    private val popupWindow by lazy {
        PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).also { p->
            p.contentView.setOnClickListener { p.dismiss() }
        }
    }


    private var selectedItemPosition: Int = -1
    private var prevItemPosition : Int = -1

    private lateinit var combListener: CustomListener

    override fun getItemId(position: Int): Long = position.toLong()

    interface CustomListener{
        fun onClick(combination: RecommendItem, position: Int)
    }

    fun setCustomListener(listener: CustomListener){
        combListener = listener
    }

    fun getPosition() : Int = selectedItemPosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        customView = LayoutInflater.from(parent.context).inflate(R.layout.layout_popup_guide, null)
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

    fun setData(data: List<RecommendItem>){
        combList.clear()
        combList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCameraCombLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private var popupVisible = false
        fun bind(combination: RecommendItem){
            binding.combination = combination
            binding.rank = adapterPosition + 1
            // Visibility 변경
            binding.itemCameraCombRecTv.visibility = if (combination.highlyRec) View.VISIBLE else View.GONE
            binding.itemCameraCombGuideLayout.visibility = if (combination.highlyRec) View.VISIBLE else View.GONE

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
            // popup window visible 보이기
            binding.itemCameraCombGuideLayout.setOnClickListener {
                if (!popupVisible)
                    PopupWindowCompat.showAsDropDown(popupWindow, binding.itemCameraCombGuideLayout,0, 10, Gravity.CENTER)
                else
                    popupWindow.dismiss()
                popupVisible = !popupVisible
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