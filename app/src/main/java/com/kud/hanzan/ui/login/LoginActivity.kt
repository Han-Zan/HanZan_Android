package com.kud.hanzan.ui.login

import android.content.Intent
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityLoginBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun initView() {

        fun isCorrectUserName(userName: String): Boolean {
            return userName.length == 13
            //TODO("닉네임 조건 결정하고 구현하기")
        }
        binding.loginUserNameET.addTextChangedListener {
            val status = binding.loginUserNameStatusTV
            if (isCorrectUserName(binding.loginUserNameET.text.toString())) {
                status.text = getString(R.string.user_name_correct)
                status.setTextColor(resources.getColor(R.color.text_positive))
            } else {
                status.text = getString(R.string.user_name_error)
                status.setTextColor(resources.getColor(R.color.text_negative))
            }
        }

        fun turnOffAllButtons(exceptAge: Int) {
            if (exceptAge != 20)
                binding.loginUserAge20.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_white_glass_button_small, null)
            if (exceptAge != 30)
                binding.loginUserAge30.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_white_glass_button_small, null)
            if (exceptAge != 40)
                binding.loginUserAge40.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_white_glass_button_small, null)
            if (exceptAge != 50)
                binding.loginUserAge50.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_white_glass_button_small, null)
            if (exceptAge != 60)
                binding.loginUserAge60over.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_white_glass_button_medium, null)
        }
        var userAge: Int = 0
        binding.loginUserAge20.setOnClickListener {
            turnOffAllButtons(20)
            it.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_yellow_glass_button_small, null)
            userAge = 20
        }
        binding.loginUserAge30.setOnClickListener {
            turnOffAllButtons(30)
            it.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_yellow_glass_button_small, null)
            userAge = 30
        }
        binding.loginUserAge40.setOnClickListener {
            turnOffAllButtons(40)
            it.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_yellow_glass_button_small, null)
            userAge = 40
        }
        binding.loginUserAge50.setOnClickListener {
            turnOffAllButtons(50)
            it.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_yellow_glass_button_small, null)
            userAge = 50
        }
        binding.loginUserAge60over.setOnClickListener {
            turnOffAllButtons(60)
            it.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_yellow_glass_button_medium, null)
            userAge = 60
        }

        binding.loginNextBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}