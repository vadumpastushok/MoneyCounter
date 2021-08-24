package com.example.moneycounter.utils

import android.content.res.Resources.getSystem

object ViewExtensions {

    val Int.toDp: Int get() = (this / getSystem().displayMetrics.density).toInt()

    val Int.toPx: Int get() = (this * getSystem().displayMetrics.density).toInt()
}