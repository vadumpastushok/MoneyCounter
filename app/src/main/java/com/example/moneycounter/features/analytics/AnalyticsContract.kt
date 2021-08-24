package com.example.moneycounter.features.analytics

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.moneycounter.base.BaseContract

interface AnalyticsContract: BaseContract {

    fun openLastFragment()

    fun setTitleText(@StringRes text: Int)

}