package com.example.moneycounter.model.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.moneycounter.model.entity.ui.IntroType

data class Intro(
    @StringRes val title:Int,
    @StringRes val text:Int,
    @DrawableRes val image:Int,
    val introType: IntroType = IntroType.STANDARD
)