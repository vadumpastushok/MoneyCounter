package com.example.moneycounter.base

interface BaseContract {

    fun updateStatusBarColor(isLight: Boolean)

    fun showToast(text: String)
}