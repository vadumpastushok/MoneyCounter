package com.example.moneycounter.features.analytics_pager

import com.example.moneycounter.R
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.features.analytics_list.AnalyticsListFragment
import com.example.moneycounter.model.entity.ui.MoneyType

class AnalyticsPagerPresenter: BasePresenter<AnalyticsPagerContract>() {

    fun onPageSelected(fragments: List<AnalyticsListFragment>, position: Int){
        if(fragments[position].moneyType== MoneyType.INCOME)rootView?.setIndicatorColor(R.color.light_blue)
        else rootView?.setIndicatorColor(R.color.red)
    }
}