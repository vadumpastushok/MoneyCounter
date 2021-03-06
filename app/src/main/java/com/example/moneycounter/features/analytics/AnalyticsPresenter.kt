package com.example.moneycounter.features.analytics

import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BasePresenter
import javax.inject.Inject

class AnalyticsPresenter @Inject constructor(): BasePresenter<AnalyticsContract>() {

    fun onBackButtonClicked(){
        rootView?.openLastFragment()
    }

    fun onGraphPageSelected(){
        rootView?.setTitleText(App.context.getString(R.string.title_analytics))
    }

    fun onListPageSelected(){
        rootView?.setTitleText(App.context.getString(R.string.title_income))
    }

    fun onPiggyBankSelected(){
        rootView?.setTitleText(App.context.getString(R.string.title_piggy_bank))
    }

}