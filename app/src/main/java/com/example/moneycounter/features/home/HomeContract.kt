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

    fun setGeneral(incomeAmount: Int, costsAmount: Int)

    fun setIncome(percent: Float, sum: Int)

    fun setCosts(percent: Float, sum: Int)

    fun setChartData(incomeAmount: Float, costsAmount: Float )

    fun openCategoriesIncome()

    fun openCategoriesCosts()

    fun openAnalytics()

    fun openCurrency()

    fun openCalculate()

    fun closeApp()

    fun getIsSidebarOpened(): Boolean

    fun openSideBar()

    fun closeSideBar()

    fun setItemEnabled(item: MenuItem, isEnabled: Boolean)

    fun setItemClickable(item: MenuItem, isClickable: Boolean)

    fun getSideBarMenuSize(): Int

    fun getSideBarMenuItem(index: Int): MenuItem

    fun openWriteToUsFragment()

    fun checkPermissionAndRequest(): Boolean

    fun openChooseFileDialog()

    fun openChooseDirectoryDialog()

    fun openLockSettingsFragment()

    fun openInfoFragment()

    fun setScrimAlpha(alpha: Float)

    fun setMainLayoutTranslation(translation: Float)
}