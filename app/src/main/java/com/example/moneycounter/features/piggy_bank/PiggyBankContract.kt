package com.example.moneycounter.features.piggy_bank

import com.example.moneycounter.base.BaseContract

interface PiggyBankContract: BaseContract {

    fun setDateChartData(leftDays: Int, passedDays: Int)

    fun showRules()

    fun hideRules()

    fun enableInvestButton()

    fun openEditText()

    fun setCostOfHour(costsOfHour: Float)
}