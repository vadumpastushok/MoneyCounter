package com.example.moneycounter.features.start_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.app.Config
import com.example.moneycounter.app.notification.AlarmHandler
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.db.DefaultData
import kotlinx.coroutines.launch

class StartScreenPresenter: BasePresenter<StartScreenContract>() {

    private val preferences by lazy { context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }

    override fun onViewAttached() {

        if(checkIsPolicyConfirmed()){
            rootView?.openHomeScreen()
        } else {
            rootView?.openIntro()
        }

        if(!checkIsDatabaseInitialized()){
            initializeDatabase()
        }

        Log.d("tag123", checkIsSoundNotificationEnabled().toString())
        if(checkIsNotificationEnabled()) {
            val alarmHandler = AlarmHandler()
            alarmHandler.setupAlarm()
        }
//        else{
//            val alarmHandler = AlarmHandler()
//            alarmHandler.removeAlarm()
//        }

    }

    private fun checkIsPolicyConfirmed() =
        preferences?.getBoolean(Config.PREF_IS_POLICY_CONFIRMED, false) ?: false

    private fun checkIsDatabaseInitialized() =
        preferences?.getBoolean(Config.PREF_IS_DATABASE_INITALIZED, false) ?: false

    private fun checkIsNotificationEnabled() =
        preferences?.getBoolean(Config.PREF_IS_NOTIFICATION_ENABLED, true) ?: true

    private fun checkIsSoundNotificationEnabled() =
        preferences?.getBoolean(Config.PREF_IS_SOUND_NOTIFICATION_ENABLED, true) ?: true

    private fun initializeDatabase(){
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()

        val databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())
        viewModelScope.launch {
            databaseManager.insertCategory(
                DefaultData.categories
            )
        }

        preferences?.edit()?.putBoolean(Config.PREF_IS_DATABASE_INITALIZED, true)?.apply()

    }
}