package com.example.moneycounter.features.category_add

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.ui.MoneyType

interface CategoryAddContract: BaseContract {

    fun openLastFragment()

    fun openIconPickerFragment()

    fun showDialog()

    fun setInDialogColor(newColor: Int)

    fun closeDialog()

    fun getTitle(): String

    fun getIcon(): String

    fun getColor(): Int

    fun getMoneyType(): MoneyType

    fun setColor(color: Int)
}