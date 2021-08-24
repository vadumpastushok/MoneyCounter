package com.example.moneycounter.features.category

import androidx.annotation.StringRes
import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.CategoryTest
import com.example.moneycounter.model.entity.ui.MoneyType
import java.util.*

interface CategoryContract: BaseContract {

    fun openAddCategoryFragment()

    fun openInputAmountFragment(id: Long)

    fun openLastFragment()

    fun getMoneyType(): MoneyType

    fun setTitleText(@StringRes text: Int)

    fun setData(list: MutableList<Category>)
}