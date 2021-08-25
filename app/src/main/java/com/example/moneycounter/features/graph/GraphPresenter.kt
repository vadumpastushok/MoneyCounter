package com.example.moneycounter.features.graph

import androidx.lifecycle.viewModelScope
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.db.GraphEntity
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch
import javax.inject.Inject

class GraphPresenter @Inject constructor(
    private val databaseManager: DatabaseManager
): BasePresenter<GraphContract>() {

    override fun onViewAttached() {
        getFinances()
    }

    private fun getFinances(){
        viewModelScope.launch {
            val finances = databaseManager.getAllFinances()

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