package com.book.homestay

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.book.homestay.presentation.home.HomeActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FCMService : FirebaseMessagingService() {
    val TITLE: String = "title"
    val IMAGE_URL: String = "image"
    val MESSAGE: String = "message"
    val SUB_TEXT: String = "sub_text"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        FirebaseMessaging.getInstance().subscribeToTopic("general")
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data[TITLE]
            val imageUrl = remoteMessage.data[IMAGE_URL]
            val message = remoteMessage.data[MESSAGE]
            val subText = remoteMessage.data[SUB_TEXT]
            notifyUser(title, imageUrl, message, subText)
        }
    }


    private fun notifyUser(title: String?, imageUrl: String?, message: String?, subText: String?) {

        val notifyId = 1
        val intent = Intent()
        intent.setComponent(ComponentName(this, HomeActivity::class.java))
        intent.putExtra(TITLE, title)
        intent.putExtra(IMAGE_URL, imageUrl)
        intent.putExtra(MESSAGE, message)
        intent.putExtra(SUB_TEXT, subText)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val name: CharSequence = this.resources.getString(R.string.app_name)
        val channelId = resources.getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_HIGH

        var builder = NotificationCompat.Builder(this, channelId)
        builder.setStyle(
            NotificationCompat.BigTextStyle().setBigContentTitle(title)
                .bigText(message)
                .setSummaryText(subText)
        )

        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
            .setContentText(message)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(soundUri)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, name, importance)
        notificationManager.createNotificationChannel(channel)
        try {
            notificationManager.notify(notifyId, builder.build())
        } catch (e: Exception) {
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseMessaging.getInstance().subscribeToTopic("general")
    }


}