package com.example.moneycounter.features.piggy_bank

import androidx.lifecycle.viewModelScope
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.features.analytics.AnalyticsFragment
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class PiggyBankPresenter @Inject constructor(
    private val databaseManager: DatabaseManager
): BasePresenter<PiggyBankContract>() {

    override fun onViewAttached() {
        setDateChartData()
        getSavedData()
        setCostOfHour()
    }

    fun onPause(){
        rootView?.hideKeyboard()
    }

    fun onBackClicked(){
        AnalyticsFragment.backClick()
    }

    /**
     * First Group
     */

    private var isEditTextOpened: Boolean = false
    fun onInvestButtonClicked(){
        if(!isEditTextOpened){
            rootView?.openEditText()
            rootView?.showKeyboard()
            isEditTextOpened = true
            isRulesShown = false
            rootView?.hideRules()
        }else{
            val amount: Int = rootView?.getAmountFromEdit() ?: 0
            rootView?.closeEditText()
            rootView?.hideKeyboard()
            isEditTextOpened = false
            if(amount!=0 ){
                saveMoney(amount)
            }
            onPercentChanged(rootView?.getCurrentPercent()?.toInt() ?: 10)
        }
    }

    private var isRulesShown: Boolean = false
    fun onShowRulesClicked(){
        isRulesShown = !isRulesShown
        if(isRulesShown){
            rootView?.showRules()
        }else{
            rootView?.hideRules()
        }
    }

    private fun setDateChartData(){
        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val passedDays = calendar[Calendar.DAY_OF_MONTH]
        val leftDays = daysInMonth - passedDays
        if(leftDays == 0){
            rootView?.enableInvestButton()
        }
        rootView?.setDateChartData(leftDays, passedDays)
    }

    private fun saveMoney(amount: Int){
        rootView?.hideKeyboard()
        savedMoney += amount
        setSavedChartData()

        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar.clear(Calendar.MINUTE)
            calendar.clear(Calendar.SECOND)
            calendar.clear(Calendar.MILLISECOND)

            val categories = databaseManager.getCategoryByType(MoneyType.COSTS)
            val piggyBankCategory = categories.find { it.title == App.context.getString(R.string.title_piggy_bank) }
            val piggyBankId = piggyBankCategory?.id ?: 0
            databaseManager.insertFinance(
                Finance(
                    piggyBankId,
                    amount,
                    calendar.timeInMillis
                )
            )
        }
    }

    /**
     * Second Group
     */

    private var isSecondGroupHidden = false
    private var averageCosts = 0
    private var savedMoney = 0
    private fun getSavedData(){
        viewModelScope.launch {
            val monthSet: MutableSet<Int> = mutableSetOf()
            var generalCosts = 0

            databaseManager.getCategoryWithFinancesByMoneyType(MoneyType.COSTS).forEach {
                item ->
                if(item.category.title != App.context.getString(R.string.title_piggy_bank)) {
                    item.finances.forEach {
                        generalCosts += it.amount
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = it.date
                        val monthCode = calendar[Calendar.MONTH] * 1000 + calendar[Calendar.YEAR]
                        monthSet.add(monthCode)
                    }
                }
            }

            val categories = databaseManager.getCategoryWithFinancesByMoneyType(MoneyType.COSTS)
            val piggyBankCategoryWithFinances = categories.find { it.category.title == App.context.getString(R.string.title_piggy_bank)}
            piggyBankCategoryWithFinances?.finances?.forEach{
                savedMoney += it.amount
            }

//            Toast.makeText(App.context, savedMoney.toString(), Toast.LENGTH_SHORT).show()

            if(monthSet.isNotEmpty()){
                averageCosts = generalCosts / monthSet.size
                setSavedChartData()
                onPercentChanged(10)
                rootView?.showSecondAndThirdGroup()
            }else{
                rootView?.showOnlyThirdGroup()
                isSecondGroupHidden = true
            }
        }
    }

    private fun setSavedChartData(){
        if(!isSecondGroupHidden) {
            val percent = savedMoney * 100 / averageCosts
            rootView?.setSavedChartData(percent)
        }
    }

    fun onPercentChanged(value: Int){
        rootView?.setSavedChartData(value)
        when {
            savedMoney == 0 -> {
                rootView?.setTitlePiggyBankEmpty()
            }
            savedMoney >= averageCosts -> {
                rootView?.setTitleAlreadyIndependent()
            }
            else -> {
                val yearPercent = value / 100f
                val requiredMoney: Float = averageCosts.toFloat()

                val years: Float = (requiredMoney / savedMoney - 1f) / yearPercent
                rootView?.setTimeIndependence(years)
            }
        }
    }

    /**
     * Third Group
     */

    private fun setCostOfHour(){
        var amount = 0f

        viewModelScope.launch {
            val monthSet: MutableSet<Int> = mutableSetOf()
            databaseManager.getCategoryWithFinancesByMoneyType(MoneyType.INCOME).forEach{
                    category ->
                category.finances.forEach {
                        finance ->
                    amount += finance.amount
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = finance.date
                    val monthCode = calendar[Calendar.MONTH] * 1000 + calendar[Calendar.YEAR]
                    monthSet.add(monthCode)
                }
            }
            val averageDaysInMonth = 30.5f
            val averageWorkDaysInMonth = averageDaysInMonth * 5f / 7f
            val averageHoursInMonth = averageWorkDaysInMonth * 8f

            val months = monthSet.size.coerceAtLeast(1)
            val hours = months * averageHoursInMonth

            val costOfHour: Float = amount / hours
            rootView?.setCostOfHour(costOfHour)
        }
    }
}