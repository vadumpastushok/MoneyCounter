package com.example.moneycounter.features.intro

import androidx.annotation.StringRes
import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.Intro

interface IntroContract : BaseContract {

    fun scrollToLast()

    fun scrollToNext()

    fun getCurrentViewPagerPosition(): Int

    fun acceptPolicyTerms()

    fun openHome()

    fun setNextButtonEnable(isEnabled: Boolean)

    fun setButtonNextText(@StringRes text: Int)

    fun setButtonSkipInvisible(isInvisible: Boolean)

    fun setData(data: MutableList<Intro>)
}