package com.kud.hanzan.application

import android.app.Application
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
    }
}