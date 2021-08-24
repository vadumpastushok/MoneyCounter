package com.example.moneycounter.features.analytics_list

import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.ui.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch


class AnalyticsListPresenter: BasePresenter<AnalyticsListContract>() {

    private lateinit var databaseManager: DatabaseManager
    private lateinit var analyticsList: MutableList<Analytics>

    override fun onViewAttached() {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())

        val root = rootView ?: return
        getAnalyticsList(root.getAnalyticsMoneyType())
    }

    fun onSortViewClicked(){
        rootView?.reverseSortImage()
        val oldList: MutableList<Analytics> = analyticsList
        analyticsList.reverse()
        setAnalyticsList(oldList, analyticsList)
    }


    private fun getAnalyticsList(moneyType: MoneyType){
        viewModelScope.launch {

            val categoriesWithFinances =
                databaseManager.getCategoryWithFinancesByMoneyType(moneyType)
            analyticsList = categoriesWithFinances
                .map { category ->
                    var amount = 0
                    for (finance in category.finances) {
                        amount += finance.amount
                    }

                    Analytics(
                        category.category.icon,
                        category.category.color,
                        category.category.title,
                        amount
                    )
                }
                .sortedByDescending { it.amount }
                .toMutableList()
            setAnalyticsList(analyticsList, analyticsList)
        }
    }

    private fun setAnalyticsList(oldList : MutableList<Analytics>, newList : MutableList<Analytics>){
        rootView?.setData(oldList, newList)
    }

}