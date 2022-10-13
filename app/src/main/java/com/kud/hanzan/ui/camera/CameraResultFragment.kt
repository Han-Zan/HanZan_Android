package com.kud.hanzan.ui.camera

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.kud.hanzan.R
import com.kud.hanzan.databinding.FragmentCameraResultBinding
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraResultFragment : BaseFragment<FragmentCameraResultBinding>(R.layout.fragment_camera_result){
    private val viewModel by viewModels<CameraViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}