package com.kud.hanzan.ui.like

import android.os.Bundle
import android.view.View
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
        observe()
    }

    private fun initView(){
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.likeCombRv.apply {
            adapter = LikeCombRVAdapter()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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