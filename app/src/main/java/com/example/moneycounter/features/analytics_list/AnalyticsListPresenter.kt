package com.example.moneycounter.features.analytics_list

import androidx.lifecycle.viewModelScope
import com.example.moneycounter.R
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.ui.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch
import javax.inject.Inject


class AnalyticsListPresenter @Inject constructor(
    private val databaseManager: DatabaseManager
): BasePresenter<AnalyticsListContract>() {

    private lateinit var analyticsList: MutableList<Analytics>

    override fun onViewAttached() {
        val root = rootView ?: return
        getAnalyticsList(root.getAnalyticsMoneyType())
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
            if(analyticsList.size != 0){
                rootView?.setRecycleData(analyticsList)
            }else{
                when(moneyType){
                    MoneyType.INCOME ->
                        rootView?.setNoDataMessage(context.getString(R.string.no_income_finances))
                    MoneyType.COSTS ->
                        rootView?.setNoDataMessage(context.getString(R.string.no_costs_finances))
                }
            }
        }
    }

    fun onBackClicked(){
        rootView?.openHomeFragment()
    }

}