package com.kud.hanzan.ui.sbti

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.user.UserApiClient
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiCheckBinding
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.utils.base.BaseActivity

class SbtiCheckActivity : BaseActivity<ActivitySbtiCheckBinding>(R.layout.activity_sbti_check) {
    override fun initView() {
        binding.name = intent.getStringExtra("user_name")
        binding.sbti = intent.getStringExtra("user_sbti")

        binding.sbtiCheckImage.setImageResource( when (binding.sbti) {
            "도전자" -> R.drawable.src_sbti_challenge
            "집돌이/집순이" -> R.drawable.src_sbti_home
            "나는 술이 좋아" -> R.drawable.src_sbti_only_drink
            "위대한 개츠비" -> R.drawable.src_sbti_party
            else -> R.drawable.src_sbti_misik
        })
        binding.sbtiCheckBriefInfo.text = resources.getString(when (binding.sbti) {
            "도전자" -> R.string.sbti_brief_challenge
            "집돌이/집순이" -> R.string.sbti_brief_home
            "나는 술이 좋아" -> R.string.sbti_brief_only_drink
            "위대한 개츠비" -> R.string.sbti_brief_party
            else -> R.string.sbti_brief_misik
        })
        binding.sbtiCheckDetailedInfo.text = resources.getString(when (binding.sbti) {
            "도전자" -> R.string.sbti_detailed_challenge
            "집돌이/집순이" -> R.string.sbti_detailed_home
            "나는 술이 좋아" -> R.string.sbti_detailed_only_drink
            "위대한 개츠비" -> R.string.sbti_detailed_party
            else -> R.string.sbti_detailed_misik
        })

        binding.sbtiCheckNextBtn.setOnClickListener {
            onBackPressed()
        }

        binding.sbtiCheckShareBtn.setOnClickListener {
            additionalPermission()
        }
    }

    private fun additionalPermission(){
        // 사용자 정보 요청 (추가 동의)

        // 사용자가 로그인 시 제3자 정보제공에 동의하지 않은 개인정보 항목 중 어떤 정보가 반드시 필요한 시나리오에 진입한다면
        // 다음과 같이 추가 동의를 받고 해당 정보를 획득할 수 있습니다.

        //  * 주의: 선택 동의항목은 사용자가 거부하더라도 서비스 이용에 지장이 없어야 합니다.

        // 사용 가능한 모든 동의 항목을 대상으로 추가 동의 필요 여부 확인 및 추가 동의를 요청하는 예제입니다.
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                var scopes = mutableListOf<String>()

                scopes.add("talk_message")

                if (scopes.isNotEmpty()) {
                    Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

                    // OpenID Connect 사용 시
                    // scope 목록에 "openid" 문자열을 추가하고 요청해야 함
                    // 해당 문자열을 포함하지 않은 경우, ID 토큰이 재발급되지 않음
                    // scopes.add("openid")

                    //scope 목록을 전달하여 카카오 로그인 요청
                    UserApiClient.instance.loginWithNewScopes(this, scopes) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 추가 동의 실패", error)
                            Toast.makeText(this, "카카오톡 메시지 보내기 권한 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                            // 사용자 정보 재요청
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", error)
                                    Toast.makeText(this, "카카오톡 메시지 보내기 권한 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                                }
                                else if (user != null) {
                                    Log.i(TAG, "사용자 정보 요청 성공")
                                    kakaoShare()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun kakaoShare() {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "${binding.name}님의 술BTI는 ${binding.sbti} 형 입니다.",
                description = "당신의 술BTI는 무엇인지 검사해보세요!",
                imageUrl = when (binding.sbti) {
                    "도전자" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_challenge.png"
                    "집돌이/집순이" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_home.png"
                    "나는 술이 좋아" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_only_drink.png"
                    "위대한 개츠비" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_party.png"
                    else -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_misik.png"
                },
                link = Link(
                    webUrl = "https://github.com/Han-Zan",
                    mobileWebUrl = "https://github.com/Han-Zan",
                )
            ),
            buttons = listOf(
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
                        iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                    )
                ),
                Button(
                    "웹에서 보기",
                    Link(
                        webUrl = "https://github.com/Han-Zan",
                        mobileWebUrl = "https://github.com/Han-Zan",
                    )
                ),
            )
        )

//        // 카카오톡 나에게 보내기
//        TalkApiClient.instance.sendDefaultMemo(defaultFeed) { error ->
//            if (error != null) {
//                Log.e(ContentValues.TAG, "나에게 보내기 실패", error)
//            } else {
//                Log.i(ContentValues.TAG, "나에게 보내기 성공")
//            }
//        }

        // 피드 메시지 보내기

        // 카카오톡 설치여부 확인
        if (ShareClient.instance.isKakaoTalkSharingAvailable(this)) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(this, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡 공유 실패", error)
                }
                else if (sharingResult != null) {
                    Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                    Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else {
            // 카카오톡 미설치: 웹 공유 사용 권장
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)

            // CustomTabs으로 웹 브라우저 열기

            // 1. CustomTabsServiceConnection 지원 브라우저 열기
            // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
            try {
                KakaoCustomTabsClient.openWithDefault(this, sharerUrl)
            } catch(e: UnsupportedOperationException) {
                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
            // ex) 다음, 네이버 등
            try {
                KakaoCustomTabsClient.open(this, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent(this, HomeActivity::class.java))
        if (!isFinishing) finish()
    }
}