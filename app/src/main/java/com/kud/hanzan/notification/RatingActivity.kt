package com.kud.hanzan.notification

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kud.hanzan.R
import com.kud.hanzan.databinding.ActivityRatingBinding
import com.kud.hanzan.ui.dialog.ConfirmDialog
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingActivity : BaseActivity<ActivityRatingBinding>(R.layout.activity_rating){
    private val viewModel by viewModels<RatingViewModel>()

    private lateinit var alarmManager : AlarmManager
    override fun initView() {
        initData()
        initListener()
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private fun initData(){
        val extras = intent.extras

        binding.drinkImg = extras?.getString("drinkImg")
        binding.foodImg = extras?.getString("foodImg")

        binding.drinkName = extras?.getString("drinkName")
        binding.foodName = extras?.getString("foodName")
        binding.combIdx = extras?.getLong("combIdx")
    }

    private fun initListener(){
        with(binding){
            lifecycleOwner= this@RatingActivity
            ratingBar.setOnRatingBarChangeListener { _, fl, b ->
                buttonVisible = b && fl > 0
            }
            ratingBarBtn.setOnClickListener {
                Toast.makeText(this@RatingActivity, "궁합 평가가 완료되었습니다", Toast.LENGTH_SHORT).show()
                // 서버에 레이팅 보내기
                combIdx?.let {
                    viewModel.postRating(it, ratingBar.rating)
                }
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