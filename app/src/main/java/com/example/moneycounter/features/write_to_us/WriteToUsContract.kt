package com.example.moneycounter.features.write_to_us

import com.example.moneycounter.base.BaseContract

interface WriteToUsContract: BaseContract {

    fun openLastFragment()

    fun openHomeFragment()

    fun setEditEnabled(isEnabled: Boolean)
}