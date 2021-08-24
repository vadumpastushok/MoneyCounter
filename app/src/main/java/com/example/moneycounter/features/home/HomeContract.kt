package com.example.moneycounter.features.home

import android.view.MenuItem
import com.example.moneycounter.base.BaseContract
import com.ssynhtn.waveview.WaveView

interface HomeContract: BaseContract {

    fun setWaves(wavesList: MutableList<WaveView.WaveData>)

    fun pauseWaves()

    fun resumeWaves()

    fun startWaves()

    fun vibrate()

    fun playSound()

    fun setGeneral(sum: Int)

    fun setIncome(percent: Float, sum: Int)

    fun setCosts(percent: Float, sum: Int)

    fun setChartData(incomeAmount: Float, costsAmount: Float )

    fun openCategoriesIncome()

    fun openCategoriesCosts()

    fun openCategoriesAnalytics()

    fun closeApp()

    fun getIsSidebarOpened(): Boolean

    fun openSideBar()

    fun closeSideBar()

    fun setItemEnabled(item: MenuItem, isEnabled: Boolean)

    fun setItemClickable(item: MenuItem, isClickable: Boolean)

    fun getSideBarMenuSize(): Int

    fun getSideBarMenuItem(index: Int): MenuItem

    fun openWriteToUsFragment()

    fun openLockSettingsFragment()

    fun openInfoFragment()

    fun onDataImported(incomeAmount: Float, costsAmount: Float)

    fun setScrimAlpha(alpha: Float)

    fun setMainLayoutTranslation(translation: Float)
}