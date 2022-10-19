package com.kud.hanzan.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialFadeThrough
import com.kud.hanzan.R
import com.kud.hanzan.adapter.HomeAlcoholRVAdapter
import com.kud.hanzan.databinding.FragmentHomeBinding
import com.kud.hanzan.domain.model.LikeAlcohol
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.utils.base.BaseFragment
import com.kud.hanzan.vision.findSimilarity
import com.kud.hanzan.vision.getTrimmedString
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reenterTransition = MaterialFadeThrough().apply { duration = 1000 }
        enterTransition = MaterialFadeThrough().apply { duration = 1000 }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 임시 데이터
        binding.user = User(1, "이동건", "고독한 미식가")
        initListener()
    }

    private fun initListener(){
        with(binding){
            homeSearch1Layout.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchActivity()
                findNavController().navigate(action)
            }
            homeTypeAlcoholRv.apply {
                adapter = HomeAlcoholRVAdapter()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            homeLikeAlcoholRv.apply {
                adapter = HomeAlcoholRVAdapter()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

//    private fun initData(){
//        // 임시
//        (binding.homeTypeAlcoholRv.adapter as HomeAlcoholRVAdapter)
//            .setData(listOf(LikeAlcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
//                LikeAlcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
//                LikeAlcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미")
//            ))
//
//        (binding.homeLikeAlcoholRv.adapter as HomeAlcoholRVAdapter)
//            .setData(listOf(LikeAlcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
//                LikeAlcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
//                LikeAlcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미")
//            ))
//    }

    @TestOnly
    private fun test(){
        val str = getTrimmedString("1. Dom Perignon Brut")
        Log.e("home", findSimilarity(str, "Dom Perignon Brut").toString())
    }
}