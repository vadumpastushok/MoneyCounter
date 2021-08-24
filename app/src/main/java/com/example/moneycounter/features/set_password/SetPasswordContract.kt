package com.example.moneycounter.features.set_password

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.moneycounter.base.BaseContract

interface SetPasswordContract: BaseContract {

    fun setCompletedLinesOnProgressbar(completedLines: Int)

    fun incorrectPassword()

    fun setTitle(@StringRes title: Int)

    fun openLastFragment()

    fun fragmentManager(): FragmentManager

    fun getFragment(): Fragment

    fun getAction(): String
}