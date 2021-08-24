package com.example.moneycounter.features.calculate

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.Currency

interface CalculateContract: BaseContract {

    fun hideKeyboard()

    fun openLastFragment()

    fun setupRecycleView(data: MutableList<Currency>)

    fun showMessage(message: String)

    fun showDialog()

    fun closeDialog()

    fun setCurrencyValue(isFirstCurrency: Boolean, value: String)

    fun setupFirstCurrency(flag: String, symbol: String)

    fun setupSecondCurrency(flag: String, symbol: String)
}