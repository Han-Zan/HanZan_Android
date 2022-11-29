package com.kud.hanzan.ui.camera

import android.content.Intent
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultVPAdapter
import com.kud.hanzan.databinding.ActivityCameraResultBinding
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraResultActivity : BaseActivity<ActivityCameraResultBinding>(R.layout.activity_camera_result){
    companion object{
        private val tabInfo = listOf("술 리스트", "안주 리스트")
    }
    private val viewModel by viewModels<CameraResultViewModel>()

    override fun initView(){
        with(binding){
            lifecycleOwner = this@CameraResultActivity
            cameraViewModel = viewModel
            cameraResultVp.apply {
                adapter = CameraResultVPAdapter(this@CameraResultActivity)
                TabLayoutMediator(cameraResultTab, cameraResultVp){
                        tab, position -> tab.text = tabInfo[position]
                }.attach()
                if (intent.hasExtra("drinkMode")){
                    if (!intent.getBooleanExtra("drinkMode", true).also {
                            viewModel.isDrinkMode = it
                        })
                        cameraResultTab.apply {
                            selectTab(getTabAt(1))
                        }
                }
            }
        }
        initListener()
        initData()
    }

    private fun initData(){
        if (intent.hasExtra("drinkList"))
            viewModel.setDrinkData(intent.getStringArrayExtra("drinkList") ?: emptyArray())
        if (intent.hasExtra("foodList"))
            viewModel.setFoodData(intent.getStringArrayExtra("foodList") ?: emptyArray())

    }

    private fun initListener(){
        with(binding){
            cameraResultNextBtn.setOnClickListener {
                startActivity(Intent(this@CameraResultActivity, CameraCombActivity::class.java).apply {
                    putExtra("drinkList", viewModel.drinkLiveData.value?.toTypedArray())
                    putExtra("foodList", viewModel.foodLiveData.value?.toTypedArray())
                })
            }
        }
    }
}