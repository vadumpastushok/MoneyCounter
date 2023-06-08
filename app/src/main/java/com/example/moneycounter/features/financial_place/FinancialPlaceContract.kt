package com.example.moneycounter.features.financial_place

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.FinancialPlace

interface FinancialPlaceContract: BaseContract {

    fun setMessageNoCategories()

    fun removeMessageNoCategories()

    fun setData(list: MutableList<FinancialPlace>)

    fun setIsEditable(editable: Boolean)

    fun notifyItemRemoved(position: Int)

    fun notifyItemInserted(position: Int)

    fun showExitDialog()

    fun showDeletingDialog()

    fun closeDialog()

    fun openLastFragment()

    fun openAddFinancePlaceFragment()

    fun openInputAmountFragment(financePlaceId: Long)
}