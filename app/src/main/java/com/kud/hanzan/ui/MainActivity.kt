package com.kud.hanzan.ui

import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityMainBinding
import com.kud.hanzan.ui.camera.CameraActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){
    override fun initView() {
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
            startActivity(Intent(this, CameraActivity::class.java))
        }
    }

}