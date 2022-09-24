package com.kud.hanzan.ui.sbti

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentSbtiTipsBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseFragment

class SbtiTipsFragment() : BaseFragment<FragmentSbtiTipsBinding>(R.layout.fragment_sbti_tips){
    var sbtiActivity: SbtiActivity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SbtiActivity) sbtiActivity = context
    }

    private fun initListener(){
        binding.sbtiTipNextBtn.setOnClickListener {
            sbtiActivity?.startSbti()
            sbtiActivity?.setProgressBar()
        }
    }
}