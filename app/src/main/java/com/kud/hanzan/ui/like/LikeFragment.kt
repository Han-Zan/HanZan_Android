package com.kud.hanzan.ui.like

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.kud.hanzan.R
import com.kud.hanzan.adapter.like.LikeVPAdapter
import com.kud.hanzan.databinding.FragmentLikeBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeFragment : BaseFragment<FragmentLikeBinding>(R.layout.fragment_like) {
    private val tabInfo = listOf("내가 찜한 술", "내가 찜한 조합")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        with(binding){
            likeTabVp.adapter = LikeVPAdapter(this@LikeFragment)
            TabLayoutMediator(likeTabTb, likeTabVp){
                tab, position -> tab.text = tabInfo[position]
            }.attach()
        }
    }
}