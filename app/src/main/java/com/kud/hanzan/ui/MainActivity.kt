package com.kud.hanzan.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityMainBinding
import com.kud.hanzan.notification.MyFirebaseMessagingService
import com.kud.hanzan.ui.camera.CameraActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){
    private var backKeyPressedTime: Long = 0

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun initView() {
        startService(Intent(applicationContext, MyFirebaseMessagingService::class.java).also {
            MyFirebaseMessagingService().getToken()
        })
        initBottomNav()
        initListener()
    }

    private fun initBottomNav(){
        binding.mainBottomNav.background = null
        val navController = supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.findNavController()
        navController?.let { binding.mainBottomNav.setupWithNavController(it) }
    }

    private fun initListener(){
        binding.mainCameraFab.setOnClickListener {
            if (allPermissionsGranted()){
                startActivity(Intent(this, CameraActivity::class.java))
            } else{
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS){
            if (allPermissionsGranted()){
                startActivity(Intent(this, CameraActivity::class.java))
            } else {
                Toast.makeText(this, "카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
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