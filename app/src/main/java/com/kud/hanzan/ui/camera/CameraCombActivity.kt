package com.kud.hanzan.ui.camera

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultCombRVAdapter
import com.kud.hanzan.databinding.ActivityCameraCombBinding
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.notification.AlarmReceiver
import com.kud.hanzan.notification.RatingActivity
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraCombActivity : BaseActivity<ActivityCameraCombBinding>(R.layout.activity_camera_comb) {
    companion object{
        private const val REQUEST_POST_NOTIFICATIONS_PERMISSIONS = 10
        private const val REQUIRED_POST_NOTIFICATIONS_PERMISSIONS = Manifest.permission.POST_NOTIFICATIONS
    }
    private lateinit var alarmManager : AlarmManager

    private var position = -1


    private val viewModel by viewModels<CameraCombViewModel>()
    override fun initView() {
        binding.lifecycleOwner = this
        binding.combViewModel = viewModel
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        binding.cameraCombRv.apply {

            adapter = CameraResultCombRVAdapter().apply {
                setCustomListener(object : CameraResultCombRVAdapter.CustomListener{
                    override fun onClick(combination: CombinationInfo, position: Int) {
                        viewModel.setEnabled(combination, position)
                    }
                })
            }
            layoutManager = LinearLayoutManager(this@CameraCombActivity, LinearLayoutManager.VERTICAL, false)

        }
        binding.cameraCombSelectBtn.setOnClickListener {
            position = (binding.cameraCombRv.adapter as CameraResultCombRVAdapter).getPosition()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2){
                if (ContextCompat.checkSelfPermission(this,
                        REQUIRED_POST_NOTIFICATIONS_PERMISSIONS
                    ) == PackageManager.PERMISSION_GRANTED)
                    sendNotification(position)
                else requestPermissions(arrayOf(REQUIRED_POST_NOTIFICATIONS_PERMISSIONS),
                    REQUEST_POST_NOTIFICATIONS_PERMISSIONS
                )
            } else {
                sendNotification(position)
            }

        }
        observe()
    }

    private fun observe(){
        viewModel.combData.observe(this){
            (binding.cameraCombRv.adapter as CameraResultCombRVAdapter).setData(it)
        }
    }

    private fun sendNotification(position: Int){
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            // Todo : 이름값 임시로 넣어둠
            // Todo : 궁합 분석화면 만들면 해당 화면에서 호출해야 함 바꿔야함, 현재는 테스트용용
            putExtra("drinkName", viewModel.combData.value?.get(position)?.drinkname).also {
                Log.e("drinkPosition", viewModel.combData.value?.get(position)?.drinkname.toString())
            }
            putExtra("foodName", viewModel.combData.value?.get(position)?.foodname)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            9000,
            pendingIntent
        )
        finishActivity()

    }

    private fun finishActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_POST_NOTIFICATIONS_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(this,
                    REQUIRED_POST_NOTIFICATIONS_PERMISSIONS
                ) == PackageManager.PERMISSION_GRANTED)
                sendNotification(position)
        }
    }
}