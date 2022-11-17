package com.kud.hanzan.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kud.hanzan.R
import com.kud.hanzan.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager
        val drinkName = intent.getStringExtra("drinkName")
        val foodName = intent.getStringExtra("foodName")
        drinkName?.let { drink -> foodName?.let { food -> sendNotification(context, drink, food) } }
    }

    private fun sendNotification(context: Context, drinkName: String, foodName: String){
        val intent = Intent(context, RatingActivity::class.java).apply {
            putExtra("notification", true)
        }
        /*
        1. FLAG_UPDATE_CURRENT : 현재 PendingIntent를 유지하고, 대신 인텐트의 extra data는 새로 전달된 Intent로 교체
        2. FLAG_CANCEL_CURRENT : 현재 인텐트가 이미 등록되어있다면 삭제, 다시 등록
        3. FLAG_NO_CREATE : 이미 등록된 인텐트가 있다면, null
        4. FLAG_ONE_SHOT : 한번 사용되면, 그 다음에 다시 사용하지 않음
         */
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = context.resources.getString(R.string.default_notification_channel_id)
        val channelName = context.resources.getString(R.string.default_notification_channel_name)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round, 2)
            .setContentTitle("궁합 평가") // 제목
            .setContentText("어제 먹었던 $drinkName-$foodName 조합은 어떠셨나요?\n별점을 매겨주세요!") // 내용
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle())
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)

        with(NotificationManagerCompat.from(context)){
            createNotificationChannel(channel)
            notify(0, builder.build())
        }
    }
}