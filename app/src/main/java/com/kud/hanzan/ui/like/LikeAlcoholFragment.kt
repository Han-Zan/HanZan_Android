package com.kud.hanzan.ui.like

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.like.LikeAlcoholRVAdapter
import com.kud.hanzan.databinding.FragmentLikeAlcoholBinding
import com.kud.hanzan.domain.model.Alcohol
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeAlcoholFragment : BaseFragment<FragmentLikeAlcoholBinding>(R.layout.fragment_like_alcohol) {
    private val viewModel by viewModels<LikeViewModel> (ownerProducer = {requireParentFragment()})
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
        initData()
    }

    private fun initListener(){
        binding.likeViewModel = viewModel
        with(binding){
            lifecycleOwner = this@LikeAlcoholFragment

            // popup menu
            val listPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listPopupWindow.apply {
                anchorView = likePreferredSortBtn
                val items = listOf("최근 찜한 순", "상품 이름 순")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                setOnItemClickListener { adapterView, view, position, id ->
                    likePreferredSortBtn.text = items[position]
                    // 팝업 닫기
                    dismiss()
                }
            }


            likePreferredSortBtn.setOnClickListener {
                listPopupWindow.show()
            }
        }
    }

    private fun initView(){
        binding.likePreferredRv.apply {
            adapter = LikeAlcoholRVAdapter()
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun initData(){
        val tempList = listOf(Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미"),
            Alcohol("참이슬", "소주", 1, 4.5, R.drawable.src_soju, "깔끔"),
            Alcohol("고든", "양주", 3, 4.9,  R.drawable.src_godons, "태그"),
            Alcohol("모스카토 다스티", "와인", 4, 4.2,  R.drawable.src_wine, "산미")
        )
        (binding.likePreferredRv.adapter as LikeAlcoholRVAdapter).setData(tempList)
    }
}