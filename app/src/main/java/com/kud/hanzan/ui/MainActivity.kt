package com.kud.hanzan.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityMainBinding
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

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

    private lateinit var cameraListener : CameraListener

    interface CameraListener{
        fun onCameraClick()
    }

    fun setListener(listener: CameraListener){
        cameraListener = listener
    }

    private fun initBottomNav(){
        binding.mainBottomNav.background = null
        navController = supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.findNavController()
        navController?.let { binding.mainBottomNav.setupWithNavController(it) }
        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.cameraFragment -> {
                    setFabStyle(true)
                }
                else -> {
                    setFabStyle(false)
                    initFab()
                }
            }
        }
        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("camera")?.observe(this){
            // Todo : 카메라 화면 왔다갔다하면 오류나는 현상 해결해보기
            // Todo : listener에 따른 처리 해야할듯?
            binding.mainCameraFab.setOnClickListener(null)
            binding.mainCameraFab.setOnClickListener {
                cameraListener.onCameraClick()
            }
            Log.e("mainCamera", it.toString())
        }
    }

    @TestOnly
    private fun setFabStyle(cameraOn: Boolean){
        binding.mainCameraFab.apply {
            if (cameraOn){
                backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                imageTintList = resources.getColorStateList(R.color.color_hanzan, null)
            } else{
                backgroundTintList = resources.getColorStateList(R.color.color_hanzan, null)
                imageTintList = ColorStateList.valueOf(Color.WHITE)
            }
        }

    }
    private fun initFab(){
        binding.mainCameraFab.setOnClickListener(null)
        binding.mainCameraFab.setOnClickListener {
            navController?.navigate(R.id.cameraFragment)
        }
    }

//    private fun getForegroundFragment() : Fragment?{
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
//        return navHostFragment?.childFragmentManager?.fragments?.get(0)
//    }

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