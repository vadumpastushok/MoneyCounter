package com.example.moneycounter.features.analytics_pager

import com.example.moneycounter.model.entity.ui.MoneyType
import org.junit.Assert.*
import org.junit.Test
import com.example.moneycounter.R

class AnalyticsPagerPresenterTest {

    @Test
    fun testTitleByIncomeMoneyType() {
        assertEquals(R.string.title_income, AnalyticsPagerPresenter().getTitleByMoneyType(MoneyType.INCOME))
    }

    @Test
    fun testTitleByCostsMoneyType() {
        assertEquals(R.string.title_costs, AnalyticsPagerPresenter().getTitleByMoneyType(MoneyType.COSTS))
    }

    @Test
    fun testColorByIncomeMoneyType() {
        assertEquals(R.color.light_blue, AnalyticsPagerPresenter().getColorByMoneyType(MoneyType.INCOME))
    }

    @Test
    fun testColorByCostsMoneyType() {
        assertEquals(R.color.red, AnalyticsPagerPresenter().getColorByMoneyType(MoneyType.COSTS))
    }
}