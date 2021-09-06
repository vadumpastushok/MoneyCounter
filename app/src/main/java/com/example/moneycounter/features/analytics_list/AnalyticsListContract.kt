package com.example.moneycounter.features.analytics_list

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.ui.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType

interface AnalyticsListContract: BaseContract {

    fun setRecycleData(list : MutableList<Analytics>)

    fun getAnalyticsMoneyType(): MoneyType

    fun setNoDataMessage(title: String)

    fun openHomeFragment()
}