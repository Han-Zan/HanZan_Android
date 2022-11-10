package com.kud.hanzan.ui.sbti

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.HanZanApplication
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
    private val userInfo : UserInfo = UserInfo("유저", "유저",-1, "잘못된", "image_error", -1, true)

    override fun initView() {
        observe()
        with(userInfo) {
            username = intent.getStringExtra("user_name")?:"유저"
            nickname = intent.getStringExtra("user_nickname")?:"유저"
            userage = intent.getIntExtra("user_age", 0)
            sbti = intent.getStringExtra("user_type")?:"잘못된"
            profileimage = intent.getStringExtra("user_profile")?:"image_error"
            kakaoId = intent.getLongExtra("user_id", 0)
            male = intent.getBooleanExtra("user_gender", true)
        }
        viewModel.login(userInfo)

        binding.user = userInfo
        binding.sbtiResultImage.setImageResource( when (userInfo.sbti) {
            "도전자" -> R.drawable.src_sbti_challenge
            "집돌이/집순이" -> R.drawable.src_sbti_home
            "나는 술이 좋아" -> R.drawable.src_sbti_only_drink
            "위대한 개츠비" -> R.drawable.src_sbti_party
            else -> R.drawable.src_sbti_misik
        })
        binding.sbtiResultBriefInfo.text = resources.getString(when (userInfo.sbti) {
            "도전자" -> R.string.sbti_brief_challenge
            "집돌이/집순이" -> R.string.sbti_brief_home
            "나는 술이 좋아" -> R.string.sbti_brief_only_drink
            "위대한 개츠비" -> R.string.sbti_brief_party
            else -> R.string.sbti_brief_misik
        })
        binding.sbtiResultDetailedInfo.text = resources.getString(when (userInfo.sbti) {
            "도전자" -> R.string.sbti_detailed_challenge
            "집돌이/집순이" -> R.string.sbti_detailed_home
            "나는 술이 좋아" -> R.string.sbti_detailed_only_drink
            "위대한 개츠비" -> R.string.sbti_detailed_party
            else -> R.string.sbti_detailed_misik
        })

        binding.sbtiResultNextBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }
    }

    private fun observe(){
        viewModel.resLiveData.observe( this) {
            if (it != null) {
                HanZanApplication.spfManager.setUserToken(it.userToken)
                HanZanApplication.spfManager.setUserIdx(it.userIdx)
            }
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