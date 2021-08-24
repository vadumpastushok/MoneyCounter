package com.example.moneycounter.model.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Analytics(
    @DrawableRes val icon : Int,
    @ColorRes val icon_color : Int,
    @StringRes val title : Int,
    val amount : Int
)
