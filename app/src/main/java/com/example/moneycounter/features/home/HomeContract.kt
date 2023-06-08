package com.example.moneycounter.features.home

import android.view.MenuItem
import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.ui.FinancialPlaceBalance
import com.ssynhtn.waveview.WaveView

interface HomeContract: BaseContract {

    fun setWaves(wavesList: MutableList<WaveView.WaveData>)

    fun pauseWaves()

    fun resumeWaves()

    fun startWaves()

    fun vibrate()

    fun playSound()

    fun setChartInfo(title: String, moneyFlow: Int, balance: Int?)

    fun setChartData(financeInfo: List<FinancialPlaceBalance>)

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