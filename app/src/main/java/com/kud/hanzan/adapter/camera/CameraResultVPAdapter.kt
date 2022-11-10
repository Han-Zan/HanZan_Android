package com.kud.hanzan.adapter.camera

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kud.hanzan.ui.camera.CameraDrinkFragment
import com.kud.hanzan.ui.camera.CameraFoodFragment

class CameraResultVPAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CameraDrinkFragment()
            else -> CameraFoodFragment()
        }
    }
}