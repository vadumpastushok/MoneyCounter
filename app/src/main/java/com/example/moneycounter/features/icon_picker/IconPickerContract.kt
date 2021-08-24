package com.example.moneycounter.features.icon_picker

import com.example.moneycounter.base.BaseContract

interface IconPickerContract: BaseContract {

    fun openCategoryAddFragment(icon: String)

    fun openLastFragment()

    fun setData(data: MutableList<String>, color: Int)

    fun getColor(): Int
}