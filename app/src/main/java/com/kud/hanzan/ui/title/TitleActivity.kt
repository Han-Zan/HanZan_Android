package com.kud.hanzan.ui.title

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityTitleBinding
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.ui.login.LoginActivity
import com.kud.hanzan.ui.sbti.SbtiActivity
import com.kud.hanzan.utils.base.BaseActivity

class TitleActivity : BaseActivity<ActivityTitleBinding>(R.layout.activity_title) {
    private fun startLogin() {
        UserApiClient.instance.me { user, error ->
            if (user != null) {
                startActivity(Intent(this, LoginActivity::class.java).apply {
                    putExtra("user_nickname", user.kakaoAccount?.profile?.nickname)
                    putExtra("user_profile", user.kakaoAccount?.profile?.thumbnailImageUrl)
                    putExtra("user_gender", user.kakaoAccount?.gender)
                })
                finishAffinity()
            }
        }
    }

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            startLogin()
        }
    }

    private fun kakaoLogin() {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

//    private fun kakaoLogout() {
//        // 로그아웃
//        UserApiClient.instance.logout { error ->
//            if (error != null) {
//                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
//            }
//            else {
//                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
//            }
//        }
//    }
//
//    private fun kakaoDelete() {
//        // 연결 끊기
//        UserApiClient.instance.unlink { error ->
//            if (error != null) {
//                Log.e(TAG, "연결 끊기 실패", error)
//            }
//            else {
//                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
//            }
//        }
//    }

    override fun initView() {
        // 사용자 정보 요청 (기본)

        UserApiClient.instance.me { user, error ->
            // TODO("사용자 데이터가 있을지 검사하고 있으면 넘어가기")
            if (user != null) {
                finishAffinity()
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                binding.titleKakaoLoginBtn.visibility = View.VISIBLE
            }
        }
        binding.titleKakaoLoginBtn.setOnClickListener{
            kakaoLogin()
        }
    }
}