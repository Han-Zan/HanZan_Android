package com.kud.hanzan.utils.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> (@LayoutRes private val layoutRes: Int): AppCompatActivity() {
    companion object{
        @JvmStatic
        protected var runningCount = 0
    }

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, layoutRes)
        initView()
        runningCount += 1
    }

    override fun onDestroy() {
        super.onDestroy()
        runningCount -= 1
    }
    abstract fun initView()
}