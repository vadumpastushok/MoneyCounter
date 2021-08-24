package com.example.moneycounter.model.entity.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Intro(
    @StringRes val title:Int,
    @StringRes val text:Int,
    @DrawableRes val image:Int,
    val introType: IntroType = IntroType.STANDARD
)