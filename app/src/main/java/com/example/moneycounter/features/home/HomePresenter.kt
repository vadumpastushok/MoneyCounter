package com.example.moneycounter.features.home

import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.random.Random

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
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())

        if(incomeAmount == 0f && costsAmount == 0f)getFinanceData() // Data has not been initialized
        else setFinanceAmount(null)
    }

    private fun getFinanceData(){
        val root = rootView ?: return

        viewModelScope.launch {
            val incomeCategories = databaseManager.getCategoryByType(MoneyType.INCOME)
            incomeAmount = 0f

            val costsCategories = databaseManager.getCategoryByType(MoneyType.COSTS)
            costsAmount = 0f

            val finances = databaseManager.getAllFinance()

            for (finance in finances) {
                if (incomeCategories.find { it.id == finance.category_id } != null) incomeAmount += finance.amount
                else costsAmount += finance.amount
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

    fun onButtonIncomeClicked(){
        rootView?.openCategoriesIncome()
    }

    fun onButtonCostsClicked(){
        rootView?.openCategoriesCosts()
    }

    fun onButtonAnalyticsClicked(){
        rootView?.openCategoriesAnalytics()
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



}