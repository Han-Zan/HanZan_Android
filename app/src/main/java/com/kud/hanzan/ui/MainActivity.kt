package com.kud.hanzan.ui

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityMainBinding
import com.kud.hanzan.ui.camera.CameraFragment
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){
    private var navController : NavController? = null
    override fun initView() {
//        startService(Intent(applicationContext, MyFirebaseMessagingService::class.java).also {
//            MyFirebaseMessagingService().getToken()
//        })
        initBottomNav()
        initScreen()
        initFab()
    }

    private fun initBottomNav(){
        binding.mainBottomNav.background = null
        navController = supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.findNavController()
        navController?.let { binding.mainBottomNav.setupWithNavController(it) }
    }

    private fun initFab(){
        binding.mainCameraFab.setOnClickListener {
            if (getForegroundFragment() is CameraFragment){
            }

            else navController?.navigate(R.id.cameraFragment)
        }
    }

    private fun getForegroundFragment() : Fragment?{
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    private fun initScreen(){
        if (intent.hasExtra("camera"))
            navController?.navigate(R.id.cameraFragment)
        else if(intent.hasExtra("place")){
            navController?.navigate(R.id.mapFragment)
        } else if(intent.hasExtra("like")){
            navController?.navigate(R.id.likeFragment)
        }
    }
}