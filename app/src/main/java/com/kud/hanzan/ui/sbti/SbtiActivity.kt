package com.kud.hanzan.ui.sbti

import android.content.Intent
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity

class SbtiActivity : BaseActivity<ActivitySbtiBinding>(R.layout.activity_sbti) {
    override fun initView() {
        binding.sbtiTipNextBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}