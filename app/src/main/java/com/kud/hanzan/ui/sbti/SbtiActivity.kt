package com.kud.hanzan.ui.sbti

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity

class SbtiActivity : BaseActivity<ActivitySbtiBinding>(R.layout.activity_sbti) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragment()
    }

    fun setFragment() {
        val sbtiTipsFragment: SbtiTipsFragment = SbtiTipsFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.sbti_tips_FL, sbtiTipsFragment)
        transaction.commit()
    }
    override fun initView() {
    }
}