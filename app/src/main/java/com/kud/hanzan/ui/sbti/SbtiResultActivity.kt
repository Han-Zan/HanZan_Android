package com.kud.hanzan.ui.sbti

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiResultBinding
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity

class SbtiResultActivity : BaseActivity<ActivitySbtiResultBinding>(R.layout.activity_sbti_result) {
    override fun initView() {
        binding.user = intent.getStringExtra("user_type")?.let {
            User(1, "이동건", it)
        } ?: run {
            User(1, "이동건", "잘못된 타입")
        }

        binding.sbtiResultNextBtn.setOnClickListener {
            // TODO("DB에 닉네임, 나이정보 POST 하기")
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
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