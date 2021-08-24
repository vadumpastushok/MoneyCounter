package com.example.moneycounter.features.home

import com.example.moneycounter.base.BaseContract
import com.ssynhtn.waveview.WaveView

interface HomeContract: BaseContract {

    fun setWaves(wavesList: MutableList<WaveView.WaveData>)

    fun startWaves()

    fun openCategoriesIncome()

    fun openCategoriesCosts()

    fun openCategoriesAnalytics()
}