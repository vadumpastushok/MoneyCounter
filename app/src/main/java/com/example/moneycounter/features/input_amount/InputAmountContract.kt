package com.example.moneycounter.features.input_amount

import com.example.moneycounter.base.BaseContract

interface InputAmountContract: BaseContract {
    fun showKeyboard()

    fun hideKeyboard()

    fun openHomeFragment()

    fun openLastFragment()
}