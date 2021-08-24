package com.example.moneycounter.features.analytics

import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BasePresenter

class AnalyticsPresenter: BasePresenter<AnalyticsContract>() {

    fun onBackButtonClicked(){
        rootView?.openLastFragment()
    }

    fun onGraphPageSelected(){
        rootView?.setTitleText(App.context.getString(R.string.title_analytics))
    }

    fun onListPageSelected(){
        rootView?.setTitleText(App.context.getString(R.string.title_analytics))
    }

    fun onPiggyBankSelected(){
        rootView?.setTitleText(App.context.getString(R.string.title_piggy_bank))
    }

}