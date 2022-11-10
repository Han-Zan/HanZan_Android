package com.kud.hanzan.ui.camera

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
            // Todo : 갔다 와서 찍으면 이미지 추가
//            cameraAlcoholAgainBtn.setOnClickListener {
//                val intent = Intent(this@CameraResultActivity, MainActivity::class.java)
//                val alcoholList = (cameraResultItemAlcoholRv.adapter as CameraResultItemRVAdapter).getItemList()
//                val drinkList = (cameraResultItemFoodRv.adapter as CameraResultItemRVAdapter).getItemList()
//                intent.putExtra("alcoholList", alcoholList)
//                intent.putExtra("foodList", drinkList)
//                setResult(RESULT_OK, intent)
//                onBackPressed()
//            }
        }
    }
}