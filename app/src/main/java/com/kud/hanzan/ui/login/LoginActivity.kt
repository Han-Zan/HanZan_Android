package com.kud.hanzan.ui.login

import android.content.Intent
import androidx.core.widget.addTextChangedListener
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityLoginBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.ui.sbti.SbtiActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun initView() {
        //TODO("Glide 라이브러리를 사용하여 프로필 사진 처리하기")

        fun isCorrectUserName(userName: String): Boolean {
            //TODO("닉네임 조건 결정하고 구현하기")
            return userName.length == 3
        }
        binding.loginUserNameET.addTextChangedListener {
            val status = binding.loginUserNameStatusTV
            if (isCorrectUserName(binding.loginUserNameET.text.toString())) {
                status.text = getString(R.string.user_name_correct)
                status.setTextColor(resources.getColor(R.color.text_positive, null))
                binding.isNameChecked = true
            } else {
                status.text = getString(R.string.user_name_error)
                status.setTextColor(resources.getColor(R.color.text_negative, null))
                binding.isNameChecked = false
            }
        }

        fun turnOffAllButtons(exceptAge: Int) {
            binding.isAgeChecked = true
            binding.loginUserAge20.isChecked = exceptAge == 20
            binding.loginUserAge30.isChecked = exceptAge == 30
            binding.loginUserAge40.isChecked = exceptAge == 40
            binding.loginUserAge50.isChecked = exceptAge == 50
            binding.loginUserAge60over.isChecked = exceptAge == 60
        }
        var userAge = 0
        binding.loginUserAge20.setOnClickListener {
            turnOffAllButtons(20)
            userAge = 20
        }
        binding.loginUserAge30.setOnClickListener {
            turnOffAllButtons(30)
            userAge = 30
        }
        binding.loginUserAge40.setOnClickListener {
            turnOffAllButtons(40)
            userAge = 40
        }
        binding.loginUserAge50.setOnClickListener {
            turnOffAllButtons(50)
            userAge = 50
        }
        binding.loginUserAge60over.setOnClickListener {
            turnOffAllButtons(60)
            userAge = 60
        }

        binding.loginNextBtn.setOnClickListener {
            // TODO("DB에 닉네임, 나이정보 POST 하기")
            startActivity(Intent(this, SbtiActivity::class.java))
        }

        binding.loginSkipBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}