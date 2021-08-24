package com.example.moneycounter.features.analytics

import com.example.moneycounter.base.BaseContract

interface AnalyticsContract: BaseContract {

    fun openLastFragment()

    fun openGraphPage()

    fun openListPage()
}