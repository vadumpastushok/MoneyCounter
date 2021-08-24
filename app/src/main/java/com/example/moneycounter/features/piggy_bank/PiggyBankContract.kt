package com.example.moneycounter.features.piggy_bank

import com.example.moneycounter.base.BaseContract

interface PiggyBankContract: BaseContract {

    fun setDateChartData(leftDays: Int, passedDays: Int)

    fun enableInvestButton()

    fun showRules()

    fun hideRules()

    fun openEditText()

    fun closeEditText()

    fun showKeyboard()

    fun hideKeyboard()

    fun getAmountFromEdit(): Int


    fun getCurrentPercent(): Float

    fun setTitleAlreadyIndependent()

    fun setTitlePiggyBankEmpty()

    fun setTimeIndependence(years: Float)


    fun hideSecondGroup()

    fun setSavedChartData(savedPercent: Int)

    fun setCostOfHour(costsOfHour: Float)
}