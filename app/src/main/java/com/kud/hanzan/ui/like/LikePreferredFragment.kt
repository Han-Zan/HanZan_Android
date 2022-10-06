package com.kud.hanzan.ui.like

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentLikePreferredBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikePreferredFragment : BaseFragment<FragmentLikePreferredBinding>(R.layout.fragment_like_preferred) {
    private val viewModel by activityViewModels<LikeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener(){
        binding.likeViewModel = viewModel
        with(binding){
            lifecycleOwner = this@LikePreferredFragment
            likeKeywordTotalCb.setOnClickListener {
                viewModel.setAllSelected()
                likeKeywordType01Cb.isChecked = false
                likeKeywordType02Cb.isChecked = false
                likeKeywordType03Cb.isChecked = false
                likeKeywordType04Cb.isChecked = false
                likeKeywordType05Cb.isChecked = false
            }
            likeKeywordType01Cb.setOnClickListener {
                if (viewModel.isAllSelected.get() == true) viewModel.setAllUnSelected()
            }
            likeKeywordType02Cb.setOnClickListener {
                if (viewModel.isAllSelected.get() == true) viewModel.setAllUnSelected()
            }
            likeKeywordType03Cb.setOnClickListener {
                if (viewModel.isAllSelected.get() == true) viewModel.setAllUnSelected()
            }
            likeKeywordType04Cb.setOnClickListener {
                if (viewModel.isAllSelected.get() == true) viewModel.setAllUnSelected()
            }
            likeKeywordType05Cb.setOnClickListener {
                if (viewModel.isAllSelected.get() == true) viewModel.setAllUnSelected()
            }

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
}