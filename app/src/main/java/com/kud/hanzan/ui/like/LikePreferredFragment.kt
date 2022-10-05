package com.kud.hanzan.ui.like

import android.os.Bundle
import android.view.View
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
        }
    }
}