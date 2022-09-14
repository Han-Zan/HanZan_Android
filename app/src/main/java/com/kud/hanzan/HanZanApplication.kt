package com.kud.hanzan

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HanZanApplication : Application() {
    companion object {
        private lateinit var application: HanZanApplication
        fun getInstance() : HanZanApplication = application
    }

    override fun onCreate(){
        super.onCreate()
        application = this

        val keyHash = Utility.getKeyHash(this)
        Log.e("keyHash", keyHash)
    }
}