package com.kud.hanzan.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kud.hanzan.R
import com.kud.hanzan.ui.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // sendTokenServer(token)
        Log.d(TAG, "FCM Token Created : $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val from = message.from
        val title = message.notification?.title
        val body = message.notification?.body

        /*
        // 데이터 페이로드가 포함된 경우
        val requestId = Integer.parseInt(message.data["requestId"])
     */
        Log.d(TAG, "message received : $message")
        Log.d(TAG, "from : $from title : $title body : $body")

        body?.let { b ->
            title?.let { t ->
                sendNotification(t, b)
            }
        }
    }

    fun getToken() : String? {
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener{ task->
            if (!task.isSuccessful){
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            token = task.result
            Log.d(TAG, "FCM Token : $token")
        })
        return token
    }

    private fun sendNotification(title: String, body:String){
        // 알림 클릭시 해당 액티비티로 오게
        val resultIntent = Intent(this, MainActivity::class.java)
        // 백 스택 만들기
        val resultPendingIntent : PendingIntent? = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setSound(defaultSound)
            .setContentText(body)
            .setContentTitle(title)
            .setContentIntent(resultPendingIntent)

        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        with(NotificationManagerCompat.from(this)){
            createNotificationChannel(channel)
            notify(0, notificationBuilder.build())
        }
    }
}