package com.kud.hanzan.ui.home

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.R
import com.kud.hanzan.adapter.home.HomeCombRVAdapter
import com.kud.hanzan.databinding.ActivityHomeBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var backKeyPressedTime: Long = 0

    // 임시
    private var userIdx : Long = 1

    companion object {
        private const val REQUEST_CAMERA_PERMISSIONS = 10
        private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )

        private const val REQUEST_PLACE_PERMISSIONS = 20
        private val REQUIRED_PLACE_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun initView() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        // Todo : UserIdx Spf 에서 받아오기
        viewModel.getData(userIdx)
        binding.homeCombRv.apply {
            adapter = HomeCombRVAdapter().apply {
                setLikeListener(object : HomeCombRVAdapter.LikeListener{
                    override fun onDelete(combIdx: Long) {
                        viewModel.deleteCombLike(userIdx, combIdx)
                    }

                    override fun onPost(combIdx: Long) {
                        viewModel.postCombLike(userIdx, combIdx)
                    }

                })
            }
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
        }
        initListener()
        observe()
    }

    private fun initListener(){
        with(binding){
            homeCameraCv.setOnClickListener {
                if (ContextCompat.checkSelfPermission(this@HomeActivity, REQUIRED_CAMERA_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED){
                    startScreen(1)
                } else{
                    requestPermissions(REQUIRED_CAMERA_PERMISSIONS, REQUEST_CAMERA_PERMISSIONS)
                }
            }
            homePlaceCv.setOnClickListener {
                if (allPermissionsGranted()){
                    startScreen(2)
                } else {
                    requestPermissions(REQUIRED_PLACE_PERMISSIONS, REQUEST_PLACE_PERMISSIONS)
                }
            }
            homeLikeCv.setOnClickListener {
                startScreen(3)
            }
            homeSearchCv.setOnClickListener {
                startScreen(0)
            }
            homeProfileIb.setOnClickListener {
                startScreen(4)
            }
        }
    }

    private fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.rankCombData.collect {
                        (binding.homeCombRv.adapter as HomeCombRVAdapter).setData(it)
                    }
                }
                launch {
                    viewModel.errorMsg.collectLatest{
                        if (it.isNotEmpty())
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(this, REQUIRED_CAMERA_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED){
                startScreen(0)
            } else {
                Toast.makeText(this, "카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        else if(requestCode == REQUEST_PLACE_PERMISSIONS){
            if (allPermissionsGranted()){
            } else{
                Toast.makeText(this, "지도 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
            }
            startScreen(1)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PLACE_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startScreen(type: Int){
        startActivity(Intent(this, MainActivity::class.java).apply {
            when(type){
                1 -> putExtra("screen", 1)
                2 -> putExtra("screen", 2)
                3 -> putExtra("screen", 3)
                4 -> putExtra("screen", 4)
            }
        })
    }


    override fun onBackPressed() {
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            // 뒤로가기 두 번 누르면 종료
            finish()
        } else{
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 가기 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}