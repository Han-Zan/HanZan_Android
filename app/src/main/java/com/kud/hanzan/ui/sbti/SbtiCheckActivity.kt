package com.kud.hanzan.ui.sbti

import android.content.Intent
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiCheckBinding
import com.kud.hanzan.ui.camera.CameraFragment
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
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent(this, HomeActivity::class.java))
        if (!isFinishing) finish()
    }
}