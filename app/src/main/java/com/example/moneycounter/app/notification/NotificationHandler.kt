package com.example.moneycounter.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.features.main.MainActivity


class NotificationHandler {
    private var channelId = ""
    private var importance = 0
    private var notificationName = ""

    fun setupNotification() {
        val preferences = App.context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE)
        val isNotificationEnabled = preferences?.getBoolean(Config.PREF_IS_NOTIFICATION_ENABLED, true) ?: true
        val isSoundEnabled =  preferences?.getBoolean(Config.PREF_IS_SOUND_NOTIFICATION_ENABLED, true) ?: true


        if(!isNotificationEnabled)return

        if (isSoundEnabled) {
            channelId = App.context.getString(R.string.channelId_with_sound)
            notificationName = App.context.getString(R.string.notification_name_with_sound)
            importance = NotificationManager.IMPORTANCE_DEFAULT
        } else {
            channelId = App.context.getString(R.string.channelId_without_sound)
            notificationName = App.context.getString(R.string.notification_name_without_sound)
            importance = NotificationManager.IMPORTANCE_LOW
        }

        showNotification(App.context.getString(R.string.notification_text))
    }

    private fun showNotification(content: String) {
        val builder = NotificationCompat.Builder(App.context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(setAction())
            .setAutoCancel(true)

        createChannel()
        with(NotificationManagerCompat.from(App.context)) {
            notify(0, builder.build())
        }
    }

    private fun setAction(): PendingIntent {
        val notificationIntent = Intent(App.context, MainActivity::class.java)

        return PendingIntent.getActivity(
            App.context, 0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    private fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = App.context.getString(R.string.descriptionText)

            val mChannel = NotificationChannel(channelId, notificationName, importance)
            mChannel.description = descriptionText

            val notificationManager = App.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}