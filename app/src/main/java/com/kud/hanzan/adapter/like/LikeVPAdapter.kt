package com.kud.hanzan.adapter.like

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kud.hanzan.ui.like.LikeAlcoholFragment
import com.kud.hanzan.ui.like.LikeCombFragment

class LikeVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LikeCombFragment()
            else -> LikeAlcoholFragment()
        }
    }
}