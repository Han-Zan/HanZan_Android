package com.kud.hanzan.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentHomeBinding
import com.kud.hanzan.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }
    private fun initListener(){
        with(binding){
            homeSearchLayout.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchActivity()
                findNavController().navigate(action)
            }
        }
    }
}