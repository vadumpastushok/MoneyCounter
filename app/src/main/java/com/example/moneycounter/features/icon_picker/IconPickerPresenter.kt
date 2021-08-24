package com.example.moneycounter.features.icon_picker

import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.db.DefaultData
import kotlinx.coroutines.launch

class IconPickerPresenter: BasePresenter<IconPickerContract>() {

    private lateinit var databaseManager: DatabaseManager

    override fun onViewAttached() {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())
        setupData()
    }

    private fun setupData(){
        viewModelScope.launch {
            val data = DefaultData.icons
            val color = rootView?.getColor() ?: 0

            rootView?.setData(data, color)
        }
    }

    fun onIconSelected(icon: String){
        rootView?.openCategoryAddFragment(icon)
    }

    fun onBackClicked(){
        rootView?.openLastFragment()
    }

}