package com.kud.hanzan

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kud.hanzan.notification.MyFirebaseMessagingService
import com.kud.hanzan.utils.SharedPreferenceUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HanZanApplication : Application() {
    companion object {
        private lateinit var application: HanZanApplication
        fun getInstance() : HanZanApplication = application
        lateinit var spfManager: SharedPreferenceUtil
    }

    override fun onCreate(){
        spfManager = SharedPreferenceUtil(applicationContext)
        super.onCreate()
        Log.e("okhttpApp", "homehome")
        application = this

        // Kakao SDK 초기화
        KakaoSdk.init(this, "f76c6d489dc7a0a0681987b5cee91ed9")
        // hashkey 얻기
        val keyHash = Utility.getKeyHash(this)
        Log.e("keyHash", keyHash)
    }
}