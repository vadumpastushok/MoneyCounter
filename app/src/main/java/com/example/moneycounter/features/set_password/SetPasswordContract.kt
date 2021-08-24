package com.example.moneycounter.features.set_password

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.ui.custom.NumberButton

interface SetPasswordContract: BaseContract {

    fun setCompletedLinesOnProgressbar(completedLines: Int)

    fun incorrectPassword()

    fun setupTable()

    fun setTitle(@StringRes title: Int)

    fun openHomeFragment()

    fun getFragment(): Fragment

    fun getFingerprintButton(): NumberButton
}