package com.example.moneycounter.features.home

import android.content.Context
import android.graphics.Color
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.features.data_manager.ExportDataManager
import com.example.moneycounter.features.data_manager.ImportDataManager
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.ui.FinancialPlaceBalance
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val databaseManager: DatabaseManager
): BasePresenter<HomeContract>() {

    @Inject
    lateinit var importDataManager: ImportDataManager

    @Inject
    lateinit var exportDataManager: ExportDataManager

    private var financeInfo = mutableListOf<FinancialPlaceBalance>()

    override fun onViewAttached() {
        rootView?.setWaves(Config.wavesData)
        rootView?.startWaves()
        getFinanceData()
        setupMenu()
    }

    private fun getFinanceData(){
        viewModelScope.launch {
            financeInfo.clear()
            val incomeCategories = databaseManager.getCategoryByType(MoneyType.INCOME)

            val financesByFinancialPlaces = databaseManager.getAllFinances().groupBy { it.placeId }

            financesByFinancialPlaces.forEach { (placeId, finances) ->
                val title = databaseManager.getAllFinancialPlaces().firstOrNull() { it.id == placeId }?.title ?: ""
                val color = databaseManager.getAllFinancialPlaces().firstOrNull() { it.id == placeId }?.color ?: Color.BLACK
                var balance = 0
                var moneyFlow = 0

                for (finance in finances) {
                    if (incomeCategories.find { it.id == finance.category_id } != null){
                        balance += finance.amount
                        moneyFlow += finance.amount
                    } else{
                        balance -= finance.amount
                        moneyFlow += finance.amount
                    }
                }
                financeInfo.add(
                    FinancialPlaceBalance(
                        title,
                        color,
                        balance,
                        moneyFlow,
                    )
                )
            }
            rootView?.setChartData(financeInfo)
            setFinanceAmount(null)
        }
    }

    private fun setFinanceAmount(title: String?){
        if (title == null) {
            rootView?.setChartInfo(
                (rootView as? Fragment)?.getString(R.string.title_general) ?: "",
                financeInfo.sumOf { it.moneyFlow },
                null
            )
            return
        }
        val financialPlace = financeInfo.firstOrNull { it.title == title } ?: return
        rootView?.setChartInfo(financialPlace.title, financialPlace.moneyFlow, financialPlace.balance)
    }

    private fun setupMenu(){
        val root = rootView ?: return
        val size = root.getSideBarMenuSize()

        for(index in 0 until size){
            val item = root.getSideBarMenuItem(index)
            if(item.title?.equals(App.context.getString(R.string.sidebar_text_notifications)) == true){
                root.setItemEnabled(item, getNotification())
            }else if(item.title?.equals(App.context.getString(R.string.sidebar_text_sound_notifications)) == true){
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

    fun onFinanceSelected(moneyFlow: Int){
        setFinanceAmount(financeInfo.firstOrNull { it.moneyFlow == moneyFlow }?.title)
    }

    fun onNothingSelected(){
        if (financeInfo.isEmpty()) {
            getFinanceData()
        } else {
           setFinanceAmount(null)
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
                if(rootView?.checkPermissionAndRequest() == true){
                    rootView?.openChooseFileDialog()
                }
            App.context.getString(R.string.sidebar_text_export_data) ->
                if(rootView?.checkPermissionAndRequest() == true){
                    rootView?.openChooseDirectoryDialog()
                }
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

        val isLightStatusBar = slideOffset >= 0.5
        rootView?.updateStatusBarColor(isLightStatusBar)

        val translation = width * slideOffset
        rootView?.setMainLayoutTranslation(translation)
    }

    private fun onDataImported(){
        getFinanceData()
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

    fun onFileImportChosen(path: String){
        val file = File(path)
        if(file.extension != "csv"){
            Toast.makeText(App.context, App.context.getString(R.string.csv_required), Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch {
            importDataManager.importDataFromFile(file)
            onDataImported()
        }

    }

    fun onDirectoryExportChosen(path: String){
        viewModelScope.launch {
            exportDataManager.exportDB(path)
        }
    }

}