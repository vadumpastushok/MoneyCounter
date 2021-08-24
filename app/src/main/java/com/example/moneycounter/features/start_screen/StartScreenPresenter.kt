package com.example.moneycounter.features.start_screen

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.db.DefaultData
import kotlinx.coroutines.launch

class StartScreenPresenter: BasePresenter<StartScreenContract>() {

    private val preferences by lazy { context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }

    override fun onViewAttached() {
        if(checkIsPoliceConfirmed()){
            rootView?.openHomeScreen()
        } else {
            rootView?.openIntro()
        }

        if(!checkIsDatabaseInitialized()){
            initializeDatabase()
        }
    }

    private fun checkIsPoliceConfirmed() =
        preferences?.getBoolean(Config.PREF_IS_POLICY_CONFIRMED, false) ?: false

    private fun checkIsDatabaseInitialized() =
        preferences?.getBoolean(Config.PREF_IS_DATABASE_INITALIZED, false) ?: false


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