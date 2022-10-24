package com.kud.hanzan.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityHomeBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var backKeyPressedTime: Long = 0

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
        binding.name = "이동건"
        initListener()
    }

    private fun initListener(){
        with(binding){
            homeCameraCv.setOnClickListener {
                if (ContextCompat.checkSelfPermission(this@HomeActivity, REQUIRED_CAMERA_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED){
                    startScreen(0)
                } else{
                    requestPermissions(REQUIRED_CAMERA_PERMISSIONS, REQUEST_CAMERA_PERMISSIONS)
                }
            }
            homePlaceCv.setOnClickListener {
                if (allPermissionsGranted()){
                    startScreen(1)
                } else {
                    requestPermissions(REQUIRED_PLACE_PERMISSIONS, REQUEST_PLACE_PERMISSIONS)
                }
            }
            homeLikeCv.setOnClickListener {
                startScreen(2)
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
                0 -> putExtra("screen", 0)
                1 -> putExtra("screen", 1)
                2 -> putExtra("screen", 2)
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