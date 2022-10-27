package com.kud.hanzan.ui.sbti

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiResultBinding
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SbtiResultActivity : BaseActivity<ActivitySbtiResultBinding>(R.layout.activity_sbti_result) {
    private val viewModel by viewModels<SbtiResultViewModel>()
    val userInfo : UserInfo = UserInfo(true, "nickname_error", "image_error", "sbti_error", "token_error", -1, "name_error")

    override fun initView() {
        observe()
        with(userInfo) {
            male = intent.getStringExtra("user_gender") == "MALE"
            nickname = intent.getStringExtra("user_nickname")?:"nickname_error"
            profileimage = intent.getStringExtra("user_profile")?:"image_error"
            sbti = intent.getStringExtra("user_type")?:"sbti_error"
            token = intent.getStringExtra("user_token")?:"token_error"
            userage = intent.getIntExtra("user_age", 0)
            username = intent.getStringExtra("user_name")?:"name_error"
        }
        viewModel.login(userInfo)

        binding.user = userInfo

        binding.sbtiResultNextBtn.setOnClickListener {
            // TODO("DB에 닉네임, 나이정보 POST 하기")
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }
    }

    private var userIdx : Long = 0
    private fun observe(){
        viewModel.userIdxLiveData.observe( this) {
            userIdx = it
            Log.e("shit", userIdx.toString())
        }
    }

    private fun kakaoDelete() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "연결 끊기 실패", error)
            }
            else {
                Log.i(ContentValues.TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    private var backKeyPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            // 뒤로가기 두 번 누르면 종료
            kakaoDelete()
            finish()
        } else{
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 가기 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}