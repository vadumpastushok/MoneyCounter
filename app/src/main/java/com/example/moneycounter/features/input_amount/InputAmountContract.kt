package com.example.moneycounter.features.input_amount

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.CategoryTest

interface InputAmountContract: BaseContract {

    fun getFragmentArgs(): InputAmountFragmentArgs

    fun showKeyboard()

    fun hideKeyboard()

    fun getAmount(): String

    fun openHomeFragment()

    fun openLastFragment()

    fun setupView(item : Category)
}