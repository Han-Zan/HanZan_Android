package com.kud.hanzan.notification

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityRatingBinding
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingActivity : BaseActivity<ActivityRatingBinding>(R.layout.activity_rating){
    private lateinit var alarmManager : AlarmManager
    override fun initView() {
        initListener()
        initData()
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private fun initData(){
        // Todo : 이미지나 클래스 보내는 것도 고려
        val extras = intent.extras
        binding.drinkName = extras?.getString("drinkName")
        binding.foodName = extras?.getString("foodName")
    }

    private fun initListener(){
        with(binding){
            lifecycleOwner= this@RatingActivity
            ratingBar.setOnRatingBarChangeListener { _, fl, b ->
                buttonVisible = b && fl > 0
            }
            ratingBarBtn.setOnClickListener {
                // Todo : 서버로 평점 데이터 연결
                if (runningCount == 1){
                    finishAfterTransition()
                    startActivity(Intent(this@RatingActivity, HomeActivity::class.java))
                } else {
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        ConfirmDialog(resources.getString(R.string.rating_dismiss_title), resources.getString(R.string.rating_dismiss_content)).apply {
            setCustomListener(object : ConfirmDialog.DialogConfirmListener{
                override fun onConfirm() {
                    dismiss()
                    if (runningCount == 1){
                        finishAfterTransition()
                        startActivity(Intent(this@RatingActivity, HomeActivity::class.java))
                    } else {
                        finish()
                    }
                }
            })
        }.show(supportFragmentManager, "ratingDialog")
    }
}