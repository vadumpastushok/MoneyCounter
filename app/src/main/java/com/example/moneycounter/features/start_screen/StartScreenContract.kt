package com.example.moneycounter.features.start_screen

import androidx.fragment.app.Fragment
import com.example.moneycounter.base.BaseContract

interface StartScreenContract: BaseContract {

    fun openHomeScreen()

    fun openIntro()

    fun setCompletedLinesOnProgressbar(completedLines: Int)

    fun incorrectPassword()

    fun setupTable()

    fun getFragment(): Fragment
}