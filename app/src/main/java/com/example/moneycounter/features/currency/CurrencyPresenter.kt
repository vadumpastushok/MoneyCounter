package com.example.moneycounter.features.currency

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.api.ApiModel
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.api.ApiCurrency
import com.example.moneycounter.model.entity.db.Currency
import com.example.moneycounter.model.entity.ui.CurrencyType
import com.mynameismidori.currencypicker.ExtendedCurrency
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CurrencyPresenter @Inject constructor(
    private val databaseManager: DatabaseManager,
    private val apiModel: ApiModel
): BasePresenter<CurrencyContract>() {

    override fun onViewAttached() {
        loadData()
        if(!isDataRecentlyUpdated()) {
            setupApiConnection()
        }
    }

    private fun loadData(){
        viewModelScope.launch {
            rootView?.setupRecycleView(databaseManager.getAllCurrencies())
        }
    }

    private fun updateData(list: MutableList<Currency>){
        viewModelScope.launch {
            databaseManager.deleteAllCurrencies()
            databaseManager.insertCurrencies(list)
            setDataIsUpdated()
        }
    }

    private val preferences by lazy { App.context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }
    private fun isDataRecentlyUpdated(): Boolean {
        val lastTime = preferences.getLong(Config.PREF_LAST_TIME_UPDATE_CURRENCIES, 0)
        val currentTime = System.currentTimeMillis()
        val fiveMinutes = 300000
        return (currentTime - lastTime) < fiveMinutes
    }

    private fun getLastTimeUpdate(): String {
        val lastTime = preferences.getLong(Config.PREF_LAST_TIME_UPDATE_CURRENCIES, 0)
        val date = Date(lastTime)

        return String.format(Locale(App.context.getString(R.string.default_locale)),
            "${App.context.getString(R.string.last_update)} %td %tB %tY ${App.context.getString(R.string.year)} ${App.context.getString(R.string.at)} %tH:%tM:%tS",date,date,date,date,date,date)
    }

    private fun setDataIsUpdated(){
        preferences.edit().putLong(Config.PREF_LAST_TIME_UPDATE_CURRENCIES, System.currentTimeMillis()).apply()
    }

    fun onCurrencyClicked(id: Long){
        rootView?.openCalculateFragment(id)
    }

    private fun setupApiConnection(){
        apiModel.getCurrency()
            .subscribe(
                { list -> handleResponse(list)},
                { checkIsRecycleEmpty()}
            )
            .let { myCompositeDisposable.add(it) }
    }

    private fun checkIsRecycleEmpty(){
        viewModelScope.launch {
            if (databaseManager.getAllCurrencies().isNullOrEmpty()) {
                rootView?.showMessage(App.context.getString(R.string.no_internet))
            } else {
                rootView?.showMessage(getLastTimeUpdate())
            }
        }
    }

    private fun handleResponse(apiCurrencyList: List<ApiCurrency>) {
        val resultList = mutableListOf<Currency>()
        resultList.add(Currency("", "", "", "", "", CurrencyType.CurrencyTitle, 1))

        val generalCurrencyList = java.util.Currency.getAvailableCurrencies()

        apiCurrencyList.forEach {
            apiCurrency ->
                val fromCurrency = generalCurrencyList.find { it.numericCode == apiCurrency.currencyCodeB }
                val toCurrency = generalCurrencyList.find { it.numericCode == apiCurrency.currencyCodeA }

                val rateBuy = apiCurrency.rateBuy
                val rateSell = apiCurrency.rateSell
                val rateCross = apiCurrency.rateCross

                val firstRate: String
                val secondRate: String

                if(rateBuy == 0f && rateSell == 0f){
                    firstRate = rateCross.toString()
                    secondRate = rateCross.toString()
                }else{
                    firstRate = rateBuy.toString()
                    secondRate = rateSell.toString()
                }

            if(fromCurrency?.currencyCode == java.util.Currency.getInstance(App.context.getString(R.string.UAH)).currencyCode &&
                toCurrency != null &&
                ExtendedCurrency.getCurrencyByISO(toCurrency.currencyCode) != null
            ) {

                val flag = App.context.resources.getResourceEntryName(ExtendedCurrency.getCurrencyByISO(toCurrency.currencyCode).flag)

                val newCurrency = Currency(
                        toCurrency.getDisplayName(Locale(App.context.getString(R.string.default_locale))),
                        toCurrency.getSymbol(Locale(App.context.getString(R.string.default_locale))),
                        flag,
                        firstRate,
                        secondRate,
                        CurrencyType.Currency,
                        (resultList.size.toLong() +1)
                    )
                    resultList.add(newCurrency)
                    if(resultList.size == 5){
                        resultList.add(Currency("", "", "", "", "", CurrencyType.CurrencySeparator, 6))
                    }
            }
        }
        rootView?.setupRecycleView(resultList)
        updateData(resultList)
    }

    fun onFragmentDestroyed(){
        myCompositeDisposable.clear()
    }

    fun onBackClicked(){
        rootView?.openLastFragment()
    }
}