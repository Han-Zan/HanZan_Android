package com.kud.hanzan.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.HomeAlcoholRVAdapter
import com.kud.hanzan.databinding.FragmentHomeBinding
import com.kud.hanzan.domain.model.Alcohol
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 임시 데이터
        binding.user = User(1, "이동건", "고독한 미식가")
        initListener()
        initData()
    }

    private fun initListener(){
        with(binding){
            homeSearchLayout.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchActivity()
                findNavController().navigate(action)
            }
            homeTypeAlcoholRv.apply {
                adapter = HomeAlcoholRVAdapter()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun initData(){
        // 임시
        (binding.homeTypeAlcoholRv.adapter as HomeAlcoholRVAdapter)
            .setData(listOf(Alcohol("고든", "양주", 3, 4.9,  R.drawable.godons, "태그"),
                Alcohol("참이슬", "소주", 1, 4.5, R.drawable.soju1, "깔끔"),
                Alcohol("모스카토 다스티", "와인", 4, 4.9,  R.drawable.wine1, "산미")
            ))
    }
}