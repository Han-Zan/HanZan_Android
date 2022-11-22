package com.kud.hanzan.ui.sbti

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.Social
import com.kud.hanzan.HanZanApplication
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiResultBinding
import com.kud.hanzan.domain.model.UserInfo
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

        binding.sbtiResultShareBtn.setOnClickListener {
            val defaultFeed = FeedTemplate(
                content = Content(
                    title = "${userInfo.username}님의 술BTI는 ${userInfo.sbti} 형 입니다.",
                    description = "당신의 술BTI는 무엇인지 검사해보세요!",
                    imageUrl = when (userInfo.sbti) {
                        "도전자" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_challenge.png"
                        "집돌이/집순이" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_home.png"
                        "나는 술이 좋아" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_only_drink.png"
                        "위대한 개츠비" -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_party.png"
                        else -> "https://hanjanbucket.s3.ap-northeast-2.amazonaws.com/src_sbti_misik.png" },
                    link = Link(
                        webUrl = "https://www.youtube.com/watch?v=Goxgot4xDDc",
                        mobileWebUrl = "https://www.youtube.com/watch?v=Goxgot4xDDc"
                    )
                ),
                buttons = listOf(
                    Button(
                        "한잔 추천받기",
                        Link(
                            webUrl = "https://www.youtube.com/watch?v=Goxgot4xDDc",
                            mobileWebUrl = "https://www.youtube.com/watch?v=Goxgot4xDDc"
                        )
                    )
                )
            )
            TalkApiClient.instance.sendDefaultMemo(defaultFeed) { error ->
                if (error != null) {
                    Log.e(TAG, "나에게 보내기 실패", error)
                } else {
                    Log.i(TAG, "나에게 보내기 성공")
                }
            }

            // 카카오톡 친구 목록 가져오기
//            TalkApiClient.instance.friends { friends, error ->
//                if (error != null) {
//                    Log.e(TAG, "카카오톡 친구 목록 가져오기 실패", error)
//                }
//                else {
//                    Log.d(TAG, "카카오톡 친구 목록 가져오기 성공 \n${friends!!.elements?.joinToString("\n")}")
//
//                    if (friends.elements?.isEmpty() == true) {
//                        Log.e(TAG, "메시지를 보낼 수 있는 친구가 없습니다")
//                    }
//                    else {
//
//                        // 서비스에 상황에 맞게 메시지 보낼 친구의 UUID를 가져오세요.
//                        // 이 예제에서는 친구 목록을 화면에 보여주고 체크박스로 선택된 친구들의 UUID 를 수집하도록 구현했습니다.
//                        FriendsActivity.startForResult(
//                            context,
//                            friends.elements.map { PickerItem(it.uuid, it.profileNickname, it.profileThumbnailImage) }
//                        ) { selectedItems ->
//                            if (selectedItems.isEmpty()) return@startForResult
//                            Log.d(TAG, "선택된 친구:\n${selectedItems.joinToString("\n")}")
//
//
//                            // 메시지 보낼 친구의 UUID 목록
//                            val receiverUuids = selectedItems
//
//                            // Feed 메시지
//                            val template = defaultFeed
//
//                            // 메시지 보내기
//                            TalkApiClient.instance.sendDefaultMessage(receiverUuids, template) { result, error ->
//                                if (error != null) {
//                                    Log.e(TAG, "메시지 보내기 실패", error)
//                                }
//                                else if (result != null) {
//                                    Log.i(TAG, "메시지 보내기 성공 ${result.successfulReceiverUuids}")
//
//                                    if (result.failureInfos != null) {
//                                        Log.d(TAG, "메시지 보내기에 일부 성공했으나, 일부 대상에게는 실패 \n${result.failureInfos}")
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
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

    private var backKeyPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            // 뒤로가기 두 번 누르면 종료
            finish()
        } else{
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 가기 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}