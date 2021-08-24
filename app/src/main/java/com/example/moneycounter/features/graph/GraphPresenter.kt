package com.example.moneycounter.features.graph

import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.db.GraphEntity
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch

class GraphPresenter: BasePresenter<GraphContract>() {
    private lateinit var databaseManager: DatabaseManager

    override fun onViewAttached() {
        val db = Room.databaseBuilder(
            App.context,
            AppDatabase::class.java,
            DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())

        getFinances()
    }

    private fun getFinances(){
        viewModelScope.launch {
            val finances = databaseManager.getAllFinance()

            rootView?.setupLineChart(MoneyType.INCOME, formatData(finances, MoneyType.INCOME))
            rootView?.setupLineChart(MoneyType.COSTS, formatData(finances, MoneyType.COSTS))
        }
    }

    private suspend fun formatData(data: MutableList<Finance>, moneyType: MoneyType): MutableMap<Long, GraphEntity?> {
        val graphDataList = data
            .map { GraphEntity(it.amount, it.date, databaseManager.getMoneyTypeById(it.category_id)) }
            .filter { it.moneyType == moneyType }
            .sortedBy { it.date }
            .toMutableList()
        val groupedMap = graphDataList.groupingBy(GraphEntity::date)
            .aggregate { _, accumulator: GraphEntity?, element: GraphEntity?, _ ->
                element?.let {
                    accumulator?.let { it.copy(
                        amount = it.amount + element.amount,
                        date = it.date,
                        moneyType = it.moneyType
                    ) } ?: element
                } ?: element
            }
        return groupedMap.toMutableMap()
    }
}