package com.example.moneycounter.features.category

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.ui.MoneyType

interface CategoryContract: BaseContract {

    fun openAddCategoryFragment()

    fun openInputAmountFragment(id: Long)

    fun openHomeFragment()

    fun getMoneyType(): MoneyType

    fun setTitleText(text: String)

    fun setData(list: MutableList<Category>)

    fun setIsEditable(editable: Boolean)

    fun notifyItemRemoved(position: Int)

    fun notifyItemInserted(position: Int)
}