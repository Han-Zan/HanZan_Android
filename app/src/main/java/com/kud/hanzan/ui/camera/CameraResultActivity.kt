package com.kud.hanzan.ui.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultItemRVAdapter
import com.kud.hanzan.adapter.camera.CameraResultVPAdapter
import com.kud.hanzan.databinding.ActivityCameraResultBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.utils.base.BaseActivity
import com.kud.hanzan.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraResultActivity : BaseActivity<ActivityCameraResultBinding>(R.layout.activity_camera_result){
    companion object{
        private val tabInfo = listOf("술 리스트", "안주 리스트")
    }
    private val viewModel by viewModels<CameraViewModel>()

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