package com.example.moneycounter.features.home

import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter

class HomePresenter: BasePresenter<HomeContract>() {

    override fun onViewAttached() {
        rootView?.setWaves(Config.wavesData)
        rootView?.startWaves()
    }

    fun onButtonIncomeClicked(){
        rootView?.openCategoriesIncome()
    }

    fun onButtonCostsClicked(){
        rootView?.openCategoriesCosts()
    }

    fun onButtonAnalyticsClicked(){
        rootView?.openCategoriesAnalytics()
    }



}