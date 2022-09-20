package com.kud.hanzan.ui.login

import android.content.Intent
import android.os.Bundle
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityLogInBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : BaseActivity<ActivityLogInBinding>(R.layout.activity_log_in) {
    override fun initView() {
        binding.loginNextBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}