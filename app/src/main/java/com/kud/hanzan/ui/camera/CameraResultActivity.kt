package com.kud.hanzan.ui.camera

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexboxLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultItemRVAdapter
import com.kud.hanzan.databinding.ActivityCameraResultBinding
import com.kud.hanzan.utils.base.BaseActivity
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraResultActivity : BaseActivity<ActivityCameraResultBinding>(R.layout.activity_camera_result){
    private val viewModel by viewModels<CameraViewModel>()

    override fun initView(){
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        with(binding){
            cameraResultItemFoodRv.apply {
                adapter = CameraResultItemRVAdapter()
                layoutManager = FlexboxLayoutManager(context)

            }

            cameraResultItemAlcoholRv.apply {
                adapter = CameraResultItemRVAdapter()
                layoutManager = FlexboxLayoutManager(context)
            }
        }

        initObserver()
    }

    private fun initObserver(){
        viewModel.alcoholLiveData.observe(this, Observer {
            (binding.cameraResultItemAlcoholRv.adapter as CameraResultItemRVAdapter).setData(it)
        })

        viewModel.foodLiveData.observe(this, Observer {
            (binding.cameraResultItemFoodRv.adapter as CameraResultItemRVAdapter).setData(it)
        })
    }

}