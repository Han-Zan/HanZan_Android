package com.kud.hanzan.adapter.ranking

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ItemDrinkBinding
import com.kud.hanzan.databinding.ItemRankingCombBinding
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.domain.model.Drink

class RankingRVAdapter : RecyclerView.Adapter<RankingRVAdapter.ViewHolder>() {
    private lateinit var binding: ItemRankingCombBinding
    private var combList = mutableListOf<CombinationInfo>()

    interface Listener {
        fun onClick(combId: Long)
        fun onDelete(combId: Long)
        fun onPost(combId: Long)
    }

    private lateinit var rankingListener : Listener

    fun setListener(listener: Listener){
        rankingListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_ranking_comb, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(combList[position], position)
        holder.itemView.setOnClickListener { rankingListener.onClick(combList[position].id) }
    }

    override fun getItemCount(): Int = combList.size

    fun setData(data: List<CombinationInfo>){
        val list = data.sortedWith(compareBy{it.pnum}).reversed()
        combList.clear()
        combList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRankingCombBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(combinationInfo: CombinationInfo, position: Int){
            binding.rank = position + 1
            binding.combination = combinationInfo
            binding.rankingCombLikeCB.isChecked = combinationInfo.isPrefer
            binding.rankingCombLikeCB.setOnClickListener {
                if (binding.rankingCombLikeCB.isChecked){
                    rankingListener.onPost(combinationInfo.id)
                    combinationInfo.pnum += 1
                    binding.combination = combinationInfo
                }
                else {
                    rankingListener.onDelete(combinationInfo.id)
                    combinationInfo.pnum -= 1
                    binding.combination = combinationInfo
                }
                combinationInfo.isPrefer = binding.rankingCombLikeCB.isChecked
            }
        }
    }
}