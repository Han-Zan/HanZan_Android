package com.kud.hanzan.ui.sbti

import android.os.Bundle
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiBinding
import com.kud.hanzan.utils.base.BaseActivity

class SbtiActivity : BaseActivity<ActivitySbtiBinding>(R.layout.activity_sbti) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragment()
    }

    private fun setFragment() {
        val sbtiTipsFragment: SbtiTipsFragment = SbtiTipsFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.sbti_FL, sbtiTipsFragment)
        transaction.commit()
    }

    fun startSbti() {
        val sbtiFragment: SbtiFragment = SbtiFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.sbti_FL, sbtiFragment)
        transaction.commit()
    }

    fun setProgressBar() {
        val sbtiProgressBarFragment: SbtiProgressBarFragment = SbtiProgressBarFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.sbti_PB, sbtiProgressBarFragment)
        transaction.commit()
    }

    override fun initView() {
    }
}