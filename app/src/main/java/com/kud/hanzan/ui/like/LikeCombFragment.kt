package com.kud.hanzan.ui.like

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentLikeCombinationBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeCombFragment : BaseFragment<FragmentLikeCombinationBinding>(R.layout.fragment_like_combination) {
    private val viewModel by viewModels<LikeViewModel> (ownerProducer = {requireParentFragment()})
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}