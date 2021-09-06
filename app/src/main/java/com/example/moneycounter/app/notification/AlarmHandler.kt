package com.example.moneycounter.app.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import com.example.moneycounter.app.App
import java.util.*


class AlarmHandler {

    fun setupAlarm(){
        val currentCalendar = Calendar.getInstance()

        val calendar: Calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 9
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0

        if(calendar.timeInMillis < currentCalendar.timeInMillis){
            calendar[Calendar.HOUR] += 12
        }
        if(calendar.timeInMillis < currentCalendar.timeInMillis){
            calendar[Calendar.HOUR] = 9
            calendar[Calendar.DAY_OF_YEAR] ++
        }

        setAlarm(calendar.timeInMillis)
    }

    private fun setAlarm(time: Long){
        val intent = Intent(App.context, NotificationBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            App.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = App.context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_HALF_DAY , pendingIntent)
    }

}