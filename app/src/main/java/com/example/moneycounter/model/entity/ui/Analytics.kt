package com.example.moneycounter.model.entity.ui

import androidx.annotation.StringRes

data class Analytics(
    val icon : String,
    val icon_color : Int,
    @StringRes val title : Int,
    val amount : Int
)
