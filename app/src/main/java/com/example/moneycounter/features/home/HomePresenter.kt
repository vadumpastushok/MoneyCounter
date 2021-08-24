package com.example.moneycounter.features.home

import android.content.Context
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.features.data_manager.ExportDataManager
import com.example.moneycounter.features.data_manager.ImportDataManager
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch

class HomePresenter: BasePresenter<HomeContract>() {
    private lateinit var databaseManager: DatabaseManager
    private var incomeAmount: Float = 0f
    private var costsAmount: Float = 0f

    override fun onViewAttached() {
        rootView?.setWaves(Config.wavesData)
        rootView?.startWaves()

        val db = Room.databaseBuilder(
            App.context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao(), db.currencyDao())

        getFinanceData()
        setupMenu()
    }

    private fun getFinanceData(){
        viewModelScope.launch {
            val incomeCategories = databaseManager.getCategoryByType(MoneyType.INCOME)
            incomeAmount = 0f
            costsAmount = 0f

            val finances = databaseManager.getAllFinances()

            for (finance in finances) {
                if (incomeCategories.find { it.id == finance.category_id } != null){
                    incomeAmount += finance.amount
                } else{
                    costsAmount += finance.amount
                }
            }

            setFinanceAmount(null)
        }
    }

    private fun setFinanceAmount(moneyType: MoneyType?){
        val root = rootView ?: return
        val amount: Float = incomeAmount + costsAmount

        root.setChartData(incomeAmount, costsAmount)
        when (moneyType) {
            null -> {
                root.setGeneral(amount.toInt())
            }
            MoneyType.INCOME -> {
                root.setIncome(incomeAmount*100/amount, incomeAmount.toInt())
            }
            else -> {
                root.setCosts(costsAmount*100/amount, costsAmount.toInt())
            }
        }
    }

    private fun setupMenu(){
        val root = rootView ?: return
        val size = root.getSideBarMenuSize()

        for(index in 0 until size){
            val item = root.getSideBarMenuItem(index)
            if(item.title.equals(App.context.getString(R.string.sidebar_text_notifications))){
                root.setItemEnabled(item, getNotification())
            }else if(item.title.equals(App.context.getString(R.string.sidebar_text_sound_notifications))){
                root.setItemEnabled(item, getNotification() && getSoundNotification())
            }
        }
    }

    fun onBackClicked(){
        if(rootView?.getIsSidebarOpened() == true){
            rootView?.closeSideBar()
        }else{
            rootView?.closeApp()
        }
    }

    fun onButtonIncomeClicked(){
        rootView?.openCategoriesIncome()
    }

    fun onButtonCostsClicked(){
        rootView?.openCategoriesCosts()
    }

    fun onButtonAnalyticsClicked(){
        rootView?.openAnalytics()
    }

    fun onButtonCurrencyClicked(){
        rootView?.openCurrency()
    }

    fun onButtonDevelopmentClicked(){
        rootView?.openCalculate()
    }

    fun onMenuButtonClicked(){
        rootView?.openSideBar()
    }

    fun onFinanceSelected(amount: Float){
        if(incomeAmount == 0f && costsAmount == 0f) {
            getFinanceData()
        }
        else if(incomeAmount == amount) {
            setFinanceAmount(MoneyType.INCOME)
        }
        else{
            setFinanceAmount(MoneyType.COSTS)
        }
    }

    fun onNothingSelected(){
        if(incomeAmount == 0f && costsAmount == 0f) {
            getFinanceData()
        }else{
            val amount = incomeAmount + costsAmount
            rootView?.setGeneral(amount.toInt())
        }
    }

    fun onSidebarItemSelected(item: MenuItem){
        when(item.title){
            App.context.getString(R.string.sidebar_text_notifications) ->
                setNotification(
                    item,
                    !getNotification()
                )
            App.context.getString(R.string.sidebar_text_sound_notifications) ->
                setSoundNotification(
                    item,
                    !getSoundNotification()
                )
            App.context.getString(R.string.sidebar_text_import_data) ->
                ImportDataManager(rootView as Fragment).importData()
            App.context.getString(R.string.sidebar_text_export_data) ->
                ExportDataManager(rootView as Fragment).exportData()
            App.context.getString(R.string.sidebar_text_lock_settings) ->
                rootView?.openLockSettingsFragment()
            App.context.getString(R.string.sidebar_text_write_to_us) ->
                rootView?.openWriteToUsFragment()
            App.context.getString(R.string.sidebar_text_info) ->
                rootView?.openInfoFragment()
        }
    }

    fun onSlide(width: Int, slideOffset: Float){
        val alpha = 0.5f.coerceAtMost(slideOffset) * 2
        rootView?.setScrimAlpha(alpha)

        val translation = width * slideOffset
        rootView?.setMainLayoutTranslation(translation)
    }

    fun onDataImported(income: Float, costs: Float){
        incomeAmount = income
        costsAmount = costs
        setFinanceAmount(null)
    }


    private val preferences by lazy { App.context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }

    private fun getNotification(): Boolean{
        return preferences?.getBoolean(Config.PREF_IS_NOTIFICATION_ENABLED, true) ?: true
    }

    private fun setNotification(item: MenuItem, isEnabled: Boolean){
        preferences?.edit()?.putBoolean(Config.PREF_IS_NOTIFICATION_ENABLED, isEnabled)?.apply()
        rootView?.setItemEnabled(item, isEnabled)
        setSoundNotificationClickable(isEnabled)
    }

    private fun getSoundNotification(): Boolean{
        return preferences?.getBoolean(Config.PREF_IS_SOUND_NOTIFICATION_ENABLED, true) ?: true
    }

    private fun setSoundNotification(item: MenuItem, isEnabled: Boolean){
        preferences?.edit()?.putBoolean(Config.PREF_IS_SOUND_NOTIFICATION_ENABLED, isEnabled)?.apply()
        rootView?.setItemEnabled(item, isEnabled)
        if(isEnabled){
            rootView?.playSound()
        }else{
            rootView?.vibrate()
        }
    }

    private fun setSoundNotificationClickable(isClickable: Boolean){
        val size = rootView?.getSideBarMenuSize() ?: 0
        for(it in 0 until size){
            val item = rootView?.getSideBarMenuItem(it) ?: continue
            if(item.title == App.context.getString(R.string.sidebar_text_sound_notifications)){
                rootView?.setItemClickable(item, isClickable)
                rootView?.setItemEnabled(item, (getSoundNotification() && isClickable))
            }
        }
    }

}