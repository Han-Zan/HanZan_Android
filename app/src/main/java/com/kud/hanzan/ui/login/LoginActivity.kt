package com.kud.hanzan.ui.login

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityLoginBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.ui.sbti.SbtiActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private fun isCorrectUserName(userName: String): Boolean {
        return userName.length in 3..8;
    }

    private fun turnOffAllButtons(exceptAge: Int) {
        binding.isAgeChecked = true
        binding.loginUserAge20.isChecked = exceptAge == 20
        binding.loginUserAge30.isChecked = exceptAge == 30
        binding.loginUserAge40.isChecked = exceptAge == 40
        binding.loginUserAge50.isChecked = exceptAge == 50
        binding.loginUserAge60over.isChecked = exceptAge == 60
    }

    private fun verifyUserName() {
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

    override fun initView() {
        Glide.with(binding.loginUserProfileIV).load(intent.getStringExtra("user_profile")).circleCrop().into(binding.loginUserProfileIV)
        binding.loginUserNameET.setText(intent.getStringExtra("user_name"))
        verifyUserName()
        binding.loginUserNameET.addTextChangedListener {
            verifyUserName()
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
            startActivity(Intent(this, SbtiActivity::class.java).apply {
                putExtra("user_name", intent.getStringExtra("user_name"))
                putExtra("user_nickname", binding.loginUserNameET.text.toString())
                putExtra("user_age", userAge)

                putExtra("user_profile", intent.getStringExtra("user_profile"))
                putExtra("user_id", intent.getLongExtra("user_id", -1))
                putExtra("user_gender", intent.getBooleanExtra("user_gender", true))
            })
        }
    }

    private fun kakaoDelete() {
        // ?????? ??????
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "?????? ?????? ??????", error)
            }
            else {
                Log.i(ContentValues.TAG, "?????? ?????? ??????. SDK?????? ?????? ?????? ???")
            }
        }
    }

    private var backKeyPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            // ???????????? ??? ??? ????????? ??????
            kakaoDelete()
            finish()
        } else{
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "?????? ?????? ??? ??? ??? ????????? ???????????????.", Toast.LENGTH_SHORT).show()
        }
    }
}