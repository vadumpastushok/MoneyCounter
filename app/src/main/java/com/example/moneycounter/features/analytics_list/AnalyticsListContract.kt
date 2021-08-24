package com.example.moneycounter.features.analytics_list

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.ui.Analytics
import com.example.moneycounter.model.entity.ui.MoneyType

interface AnalyticsListContract: BaseContract {

    fun setData(oldList : MutableList<Analytics>, newList : MutableList<Analytics>)

    fun reverseSortImage()

    fun getAnalyticsMoneyType(): MoneyType

}