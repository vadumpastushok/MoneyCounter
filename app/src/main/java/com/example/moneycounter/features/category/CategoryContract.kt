package com.example.moneycounter.features.category

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.MoneyType

interface CategoryContract: BaseContract {

    fun openInputAmountFragment(id: Int)

    fun openLastFragment()

    fun getMoneyType(): MoneyType

    fun setupPager()

    fun setupIncome()

    fun setupCosts()
}