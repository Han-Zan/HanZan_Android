package com.kud.hanzan.ui.sbti

import android.content.Intent
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivitySbtiResultBinding
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.ui.MainActivity
import com.kud.hanzan.utils.base.BaseActivity

class SbtiResultActivity : BaseActivity<ActivitySbtiResultBinding>(R.layout.activity_sbti_result) {
    override fun initView() {
        binding.user = User(1, "이동건", "고독한 미식가")
    }
}