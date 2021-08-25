package com.example.moneycounter.features.intro

import android.content.Context
import com.example.moneycounter.R
import com.example.moneycounter.app.App.Companion.context
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter
import javax.inject.Inject

class IntroPresenter @Inject constructor() : BasePresenter<IntroContract>() {
    private var isPolicyConfirmed: Boolean = false

    override fun onViewAttached() {
        rootView?.setData(Config.introData)
    }

    fun onCancelIntroClicked() {
        rootView?.scrollToLast()
    }

    fun onNextPageClicked() {
        val nextPosition = rootView?.getCurrentViewPagerPosition()?.plus(1) ?: return
        if(nextPosition < Config.introData.size) {
            rootView?.scrollToNext()
        } else {
            val preferences = context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE)
            preferences.edit().putBoolean(Config.PREF_IS_POLICY_CONFIRMED, true).apply()
            rootView?.openHome()
        }
    }

    fun acceptPolicyTerms(){
        isPolicyConfirmed = true
        rootView?.setNextButtonEnable(true)
    }

    fun onPageSelected(){
        val currentPosition = rootView?.getCurrentViewPagerPosition()
        val lastPosition = Config.introData.size.minus(1)
        rootView?.apply {
            if (currentPosition == lastPosition) {
                setButtonSkipInvisible(true)
                setButtonNextText(R.string.intro_accept_policy_terms)
                setNextButtonEnable(isPolicyConfirmed)
            } else {
                setButtonSkipInvisible(false)
                setButtonNextText(R.string.next)
                setNextButtonEnable(true)
            }
        }
    }
}