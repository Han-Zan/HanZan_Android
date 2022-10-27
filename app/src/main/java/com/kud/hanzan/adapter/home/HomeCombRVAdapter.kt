package com.kud.hanzan.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemHomeCombBinding
import com.kud.hanzan.domain.model.HomeComb

class HomeCombRVAdapter : RecyclerView.Adapter<HomeCombRVAdapter.ViewHolder>() {
    private val combList = ArrayList<HomeComb>()
    private lateinit var binding : ItemHomeCombBinding

    interface LikeListener{
        fun onDelete(combIdx: Long)
        fun onPost(combIdx: Long)
    }

    private lateinit var likeListener : LikeListener

    fun setLikeListener(listener: LikeListener){
        likeListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_home_comb, parent, false)

        val height = parent.measuredHeight / combList.size
        val width = parent.measuredWidth

        binding.root.layoutParams = RecyclerView.LayoutParams(width, height)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(combList[position], position)
    }

    override fun getItemCount(): Int = combList.size

    fun setData(data: List<HomeComb>){
        combList.clear()
        combList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding : ItemHomeCombBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(combination: HomeComb , position: Int){
            binding.combination = combination
            binding.rank = position + 1
            binding.itemCombRankCb.setOnClickListener {
                if (!binding.itemCombRankCb.isChecked)
                    likeListener.onDelete(combination.id)
                else  likeListener.onPost(combination.id)

                combination.is_prefer = binding.itemCombRankCb.isChecked
            }
        }
    }
}