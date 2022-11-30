package com.kud.hanzan.ui.camera

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kud.hanzan.R
import com.kud.hanzan.adapter.camera.CameraResultCombRVAdapter
import com.kud.hanzan.databinding.ActivityCameraCombBinding
import com.kud.hanzan.domain.model.CombinationInfo
import com.kud.hanzan.domain.model.RecommendItem
import com.kud.hanzan.notification.AlarmReceiver
import com.kud.hanzan.notification.RatingActivity
import com.kud.hanzan.ui.home.HomeActivity
import com.kud.hanzan.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CameraCombActivity : BaseActivity<ActivityCameraCombBinding>(R.layout.activity_camera_comb) {
    private val calendar by lazy {
        Calendar.getInstance()
    }

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
        initData()

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        binding.cameraCombRv.apply {

            adapter = CameraResultCombRVAdapter().apply {
                setCustomListener(object : CameraResultCombRVAdapter.CustomListener{
                    override fun onClick(combination: RecommendItem, position: Int) {
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

    private fun initData(){
        viewModel.getRecommend(intent.getStringArrayExtra("drinkList")?.toList() ?: emptyList(),
            intent.getStringArrayExtra("foodList")?.toList().also { Log.e("foodList", it.toString()) } ?: emptyList()
        )
    }

    private fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.combData.collectLatest{
                        Log.e("combOkhttpBefore", it.toString())
                        (binding.cameraCombRv.adapter as CameraResultCombRVAdapter).setData(it)
                    }
                }
            }
        }
    }

    private fun sendNotification(position: Int){
        finishAfterTransition()

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            val combination = viewModel.combData.value?.get(position)
            putExtra("drinkImg", combination?.drinkImg)
            putExtra("foodImg", combination?.foodImg)
            putExtra("drinkName", combination?.drinkName)
            putExtra("foodName", combination?.foodName)
            putExtra("combIdx", combination?.combId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Todo : 현재 9초 후로 되어있는 것 수정하기
        calendar.add(Calendar.SECOND, 9)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Toast.makeText(this, "궁합 선택이 완료되었습니다", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, HomeActivity::class.java))
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