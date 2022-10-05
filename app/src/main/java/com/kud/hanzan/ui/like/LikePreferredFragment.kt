package com.kud.hanzan.ui.like

import android.os.Bundle
import android.view.View
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentLikePreferredBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikePreferredFragment : BaseFragment<FragmentLikePreferredBinding>(R.layout.fragment_like_preferred) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}