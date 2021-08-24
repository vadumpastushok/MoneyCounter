package com.example.moneycounter.model.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlin.random.Random

data class Category(
    @DrawableRes val icon: Int,
    @ColorRes val color: Int,
    @StringRes val title: Int,
    val id: Int = Random.nextInt()
)