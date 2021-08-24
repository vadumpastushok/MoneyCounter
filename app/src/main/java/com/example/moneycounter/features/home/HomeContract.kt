package com.example.moneycounter.features.home

import com.example.moneycounter.base.BaseContract
import com.ssynhtn.waveview.WaveView

interface HomeContract: BaseContract {

    fun setWaves(wavesList: MutableList<WaveView.WaveData>)

    fun startWaves()

    fun setGeneral(sum: Int)

    fun setIncome(percent: Float, sum: Int)

    fun setCosts(percent: Float, sum: Int)

    fun setChartData(incomeAmount: Float, costsAmount: Float )

    fun openCategoriesIncome()

    fun openCategoriesCosts()

    fun openCategoriesAnalytics()
}