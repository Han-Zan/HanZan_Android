package com.kud.hanzan.notification

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
    companion object{
        private const val REQUEST_POST_NOTIFICATIONS_PERMISSIONS = 10
        private const val REQUIRED_POST_NOTIFICATIONS_PERMISSIONS = Manifest.permission.POST_NOTIFICATIONS
    }

    private lateinit var alarmManager : AlarmManager
    override fun initView() {
        initListener()
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private fun initListener(){
        with(binding){
            lifecycleOwner= this@RatingActivity
            ratingBar.setOnRatingBarChangeListener { _, fl, b ->
                buttonVisible = b && fl > 0
            }
            // Todo : 임시 푸시알림 테스트
            ratingNotificationTestBtn.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2){
                    if (ContextCompat.checkSelfPermission(this@RatingActivity, REQUIRED_POST_NOTIFICATIONS_PERMISSIONS) == PackageManager.PERMISSION_GRANTED)
                        sendNotification()
                    else requestPermissions(arrayOf(REQUIRED_POST_NOTIFICATIONS_PERMISSIONS), REQUEST_POST_NOTIFICATIONS_PERMISSIONS)
                } else {
                    sendNotification()
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

    // Todo : Alarm 설정
    private fun sendNotification(){
        val intent = Intent(this@RatingActivity,AlarmReceiver::class.java).apply {
            // Todo : 이름값 임시로 넣어둠
            // Todo : 궁합 분석화면 만들면 해당 화면에서 호출해야 함 바꿔야함, 현재는 테스트용용
            putExtra("drinkName", "소주")
            putExtra("foodName", "닭발")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this@RatingActivity, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            9000,
            pendingIntent
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_POST_NOTIFICATIONS_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(this, REQUIRED_POST_NOTIFICATIONS_PERMISSIONS) == PackageManager.PERMISSION_GRANTED)
                sendNotification()
        }
    }
}