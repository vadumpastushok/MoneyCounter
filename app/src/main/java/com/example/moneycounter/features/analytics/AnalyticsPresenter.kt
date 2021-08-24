package com.example.moneycounter.features.analytics

import com.example.moneycounter.base.BasePresenter

class AnalyticsPresenter: BasePresenter<AnalyticsContract>() {

    fun onBackButtonClicked(){
        rootView?.openLastFragment()
    }

    fun onGraphPageSelected(){
        rootView?.openGraphPage()
    }

    fun onListPageSelected(){
        rootView?.openListPage()
    }
}