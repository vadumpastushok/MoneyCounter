package com.example.moneycounter.features.analytics_pager

import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.features.analytics.AnalyticsFragment
import com.example.moneycounter.analytics_list.AnalyticsListFragment
import com.example.moneycounter.model.entity.ui.MoneyType
import javax.inject.Inject

class AnalyticsPagerPresenter @Inject constructor(): BasePresenter<AnalyticsPagerContract>() {

    fun onPageSelected(fragments: List<AnalyticsListFragment>, position: Int){
        rootView?.setIndicatorColor(getColorByMoneyType(fragments[position].moneyType))
        AnalyticsFragment.setupTitleText(App.context.getString(getTitleByMoneyType(fragments[position].moneyType)))
    }

    fun getColorByMoneyType(moneyType: MoneyType): Int {
        return if (moneyType == MoneyType.INCOME) {
            R.color.light_blue
        } else {
            R.color.red
        }
    }

    fun getTitleByMoneyType(moneyType: MoneyType): Int {
        return if (moneyType == MoneyType.INCOME) {
            R.string.title_income
        } else {
            R.string.title_costs
        }
    }
}