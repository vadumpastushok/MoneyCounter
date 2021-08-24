package com.example.moneycounter.features.start_screen

import androidx.fragment.app.Fragment
import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.ui.custom.NumberButton

interface StartScreenContract: BaseContract {
    fun openHomeScreen()

    fun openIntro()

    fun setCompletedLinesOnProgressbar(completedLines: Int)

    fun incorrectPassword()

    fun setupTable()

    fun getFingerprintButton(): NumberButton

    fun getFragment(): Fragment
}