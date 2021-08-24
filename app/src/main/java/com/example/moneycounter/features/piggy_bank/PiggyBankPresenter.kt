package com.example.moneycounter.features.piggy_bank

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.app.App
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import kotlinx.coroutines.launch
import java.util.*

class PiggyBankPresenter: BasePresenter<PiggyBankContract>() {

    private lateinit var databaseManager: DatabaseManager

    override fun onViewAttached() {
        val db = Room.databaseBuilder(
            App.context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao())

        setDateChartData()
        setCostOfHour()
    }


    private var isEditTextOpened: Boolean = false
    fun onInvestButtonClicked(){
        if(!isEditTextOpened){
            rootView?.openEditText()
        }else{
            Toast.makeText(App.context,"21312123123",Toast.LENGTH_SHORT).show()
        }
    }




    private var isLastDay: Boolean = false
    private fun setDateChartData(){
        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val passedDays = calendar[Calendar.DAY_OF_MONTH]
        val leftDays = daysInMonth - passedDays
        if(leftDays == 0){
            isLastDay = true
            rootView?.enableInvestButton()
        }
        rootView?.setDateChartData(leftDays, passedDays)
    }


    private fun setCostOfHour(){
        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val time: Float = daysInMonth * 24f
        var amount: Float = 0f

        viewModelScope.launch {
            databaseManager.getAllFinance().forEach{amount += it.amount}
            val costOfHour: Float = amount/time
            rootView?.setCostOfHour(costOfHour)
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




}