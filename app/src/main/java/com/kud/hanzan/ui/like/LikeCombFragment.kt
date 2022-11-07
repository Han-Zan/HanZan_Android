package com.kud.hanzan.ui.like

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.like.LikeCombRVAdapter
import com.kud.hanzan.databinding.FragmentLikeCombinationBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LikeCombFragment : BaseFragment<FragmentLikeCombinationBinding>(R.layout.fragment_like_combination) {
    private val viewModel by viewModels<LikeViewModel> (ownerProducer = {requireParentFragment()})
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        observe()
    }

    private fun initView(){
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.likeCombRv.apply {
            adapter = LikeCombRVAdapter().apply {
                setLikeListener(object : LikeCombRVAdapter.LikeListener{
                    override fun onDelete(combId: Long) {
                        viewModel.deleteComb(combId)
                    }

                    override fun onPost(combId: Long) {
                        viewModel.postComb(combId)
                    }
                })
            }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initListener(){
        with(binding){
            val listAlcoholPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listAlcoholPopupWindow.apply {
                anchorView = likeCombSortAlcoholBtn
                val items = listOf(" 소주 ", " 맥주 ", " 양주 ", " 와인 ", " 기타 ")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    likeCombSortAlcoholBtn.text = items[position]
                    // 팝업 닫기
                    dismiss()
                }
            }

            // 임시 팝업 메뉴뉴
            val popupMenu = PopupMenu(context, likeCombSortAlcoholBtn)
            popupMenu.menu.apply {
                add("소주")
                add("맥주")
                add("양주")
                add("와인")
                add("기타")
            }
            popupMenu.setOnMenuItemClickListener {
                likeCombSortAlcoholBtn.text = it.title
                true
            }

            val listFoodPopupWindow = ListPopupWindow(requireContext(), null, com.google.android.material.R.attr.listPopupWindowStyle)
            listFoodPopupWindow.apply {
                anchorView = likeCombSortFoodBtn
                val items = listOf("고기류", "과일류", "튀김류", "매운 음식", " 기타 ")
                val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_like_sort, items)
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    likeCombSortFoodBtn.text = items[position]
                    // 팝업 닫기
                    dismiss()
                }
            }

            likeCombSortAlcoholBtn.setOnClickListener { listAlcoholPopupWindow.show() }
            likeCombSortFoodBtn.setOnClickListener { listFoodPopupWindow.show() }
        }
    }

    private fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.combData.collectLatest {
                    (binding.likeCombRv.adapter as LikeCombRVAdapter).setData(it)
                }
            }
        }
    }
}