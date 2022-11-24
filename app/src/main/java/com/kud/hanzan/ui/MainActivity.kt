package com.kud.hanzan.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityMainBinding
import com.kud.hanzan.ui.camera.CameraFragment
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.ui.profile.ProfileFragment
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){
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

    private var navController : NavController? = null
    override fun initView() {
//        startService(Intent(applicationContext, MyFirebaseMessagingService::class.java).also {
//            MyFirebaseMessagingService().getToken()
//        })
        initBottomNav()
        initFab()
    }

    private var cameraListener : CameraListener? = null

    interface CameraListener{
        fun onCameraClick()
    }

    fun setListener(listener: CameraListener){
        cameraListener = null
        cameraListener = listener
    }

    fun fabCameraListener(){
        binding.mainCameraFab.setOnClickListener(null)
        binding.mainCameraFab.setOnClickListener {
            cameraListener?.onCameraClick()
        }
    }

    private fun initBottomNav(){
        binding.mainBottomNav.background = null
        navController = supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.findNavController()

        if (intent.hasExtra("screen")){
            val inflater = navController?.navInflater
            val graph = inflater?.inflate(R.navigation.main_bottom_graph)
            graph?.setStartDestination(
                when(intent.getIntExtra("screen", 0)){
                    1 -> R.id.cameraFragment
                    2 -> R.id.mapFragment
                    3 -> R.id.likeFragment
                    4 -> R.id.profileFragment
                    else -> R.id.rankingFragment
                }
            )
            graph?.let { navController?.graph = it }
        }


        navController?.let { binding.mainBottomNav.setupWithNavController(it) }
        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.cameraFragment -> {
                    setFabStyle(true)
                    if (ContextCompat.checkSelfPermission(this@MainActivity, REQUIRED_CAMERA_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED){
                    } else{
                        requestPermissions(
                            REQUIRED_CAMERA_PERMISSIONS,
                            REQUEST_CAMERA_PERMISSIONS
                        )
                    }
                }
                R.id.mapFragment -> {
                    setFabStyle(false)
                    initFab()
                    if (allPermissionsGranted()){
                    }
                    else{
                        requestPermissions(
                            REQUIRED_PLACE_PERMISSIONS,
                            REQUEST_PLACE_PERMISSIONS
                        )
                    }
                }
                else -> {
                    setFabStyle(false)
                    initFab()
                }
            }
        }
    }

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
            navController?.popBackStack()
            navController?.navigate(R.id.cameraFragment)
        }
    }

    private fun getForegroundFragment() : Fragment?{
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    override fun onBackPressed() {
        if (getForegroundFragment() is CameraFragment){
            finish()
        } else{
            super.onBackPressed()
            setResult(RESULT_OK, Intent(this, HomeActivity::class.java))
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
            } else {
                Toast.makeText(this, "카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
        else if(requestCode == REQUEST_PLACE_PERMISSIONS){
            if (allPermissionsGranted()){
            } else{
                Toast.makeText(this, "지도 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PLACE_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}