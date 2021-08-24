package com.example.moneycounter.app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationHandler = NotificationHandler()
        if (context != null) {
            notificationHandler.setupNotification()
        }
    }
}