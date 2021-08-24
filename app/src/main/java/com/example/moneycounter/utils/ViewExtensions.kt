package com.example.moneycounter.utils

import android.content.res.Resources.getSystem

object ViewExtensions {
    val Int.dp: Int get() = (this * getSystem().displayMetrics.density).toInt()
}