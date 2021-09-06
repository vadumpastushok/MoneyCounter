package com.example.moneycounter.features.calculate

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.Currency

interface CalculateContract: BaseContract {

    fun getCurrencyId(): Long

    fun hideKeyboard()

    fun openLastFragment()

    fun setData(data: MutableList<Currency>)

    fun showMessage(message: String)

    fun showDialog()

    fun closeDialog()

    fun setFirstCurrencyValue(value: String)

    fun setSecondCurrencyValue(value: String)

    fun setupFirstCurrency(flag: String, symbol: String)

    fun setupSecondCurrency(flag: String, symbol: String)
}