package com.example.moneycounter.features.calculate

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.moneycounter.R
import com.example.moneycounter.app.App
import com.example.moneycounter.app.Config
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.api.GetCurrency
import com.example.moneycounter.model.db.AppDatabase
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.db.DatabaseManager
import com.example.moneycounter.model.entity.api.ApiCurrency
import com.example.moneycounter.model.entity.db.Currency
import com.example.moneycounter.model.entity.ui.CurrencyType
import com.mynameismidori.currencypicker.ExtendedCurrency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class CalculatePresenter: BasePresenter<CalculateFragment>() {

    private lateinit var databaseManager: DatabaseManager
    private val preferences by lazy { App.context.getSharedPreferences(Config.PREFERENCES_NAME, Context.MODE_PRIVATE) }
    override fun onViewAttached() {
        val db = Room.databaseBuilder(
            App.context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
        databaseManager = DatabaseManager(db.categoryDao(), db.financeDao(), db.currencyDao())
        loadPrevData()
        loadDataFromDatabase()
        if(checkLastTimeUpdate()){
            loadFromAPI()
        }
    }

    private fun loadPrevData(){
        viewModelScope.launch {
            val currency = databaseManager.getAllCurrencies().find { it.symbol == App.context.getString(R.string.USD) } ?: return@launch
            rootView?.setupSecondCurrency(currency.flag, currency.symbol)
            secondRate = currency.secondRate.toFloat()
        }
    }

    private fun updatePrevData(list: MutableList<Currency>){
        val currency = list.find { it.symbol == App.context.getString(R.string.USD) } ?: return
        rootView?.setupSecondCurrency(currency.flag, currency.symbol)
        secondRate = currency.secondRate.toFloat()
    }

    private fun updateData(list: MutableList<Currency>){
        viewModelScope.launch {
            databaseManager.insertCurrencies(list)
            setDataIsUpdated()
        }
    }

    private fun setDataIsUpdated(){
        preferences.edit().putLong(Config.PREF_LAST_TIME_UPDATE_CURRENCIES, System.currentTimeMillis()).apply()
    }

    private fun checkLastTimeUpdate(): Boolean{
        val lastTime = preferences.getLong(Config.PREF_LAST_TIME_UPDATE_CURRENCIES, 0)
        val currentTime = System.currentTimeMillis()
        val fiveMinutes = 300000
        return currentTime - lastTime > fiveMinutes
    }

    private fun getLastTimeUpdate(): String {
        val lastTime = preferences.getLong(Config.PREF_LAST_TIME_UPDATE_CURRENCIES, 0)
        val date = Date(lastTime)

        return String.format(Locale(App.context.getString(R.string.ru)),
            "${App.context.getString(R.string.last_update)} %td %tB %tY ${App.context.getString(R.string.year)} ${App.context.getString(R.string.at)} %tH:%tM:%tS",date,date,date,date,date,date)
    }

    private fun loadDataFromDatabase(){
        viewModelScope.launch {
            val list = databaseManager.getAllCurrencies()
            rootView?.setupRecycleView(list)
        }
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

    private var myCompositeDisposable: CompositeDisposable? = null
    private fun loadFromAPI(){
        myCompositeDisposable = CompositeDisposable()

        val requestInterface = Retrofit.Builder()
            .baseUrl(App.context.getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetCurrency::class.java)


        myCompositeDisposable?.add(requestInterface.getCurrency()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { list -> handleResponse(list)},
                { checkIsRecycleEmpty()}
            ))
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
                    toCurrency.getDisplayName(Locale(App.context.getString(R.string.ru))),
                    toCurrency.getSymbol(Locale(App.context.getString(R.string.ru))),
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

        val newList = mutableListOf<Currency>()
        newList += resultList
        rootView?.setupRecycleView(newList)
        updateData(resultList)
        updatePrevData(resultList)
    }


    private var firstRate = 1f
    private var secondRate = 1f

    private var firstValue = 0f
    private var secondValue = 0f

    private var isFirstCurrencyLastChanged = true

    private fun updateCurrencyValues(){
        if(isFirstCurrencyLastChanged){
            secondValue = firstValue * firstRate / secondRate
            if(secondValue == 0f){
                rootView?.setCurrencyValue(false, "")
            }else {
                rootView?.setCurrencyValue(false, secondValue.toString())
            }
        }else{
            firstValue = secondValue * secondRate / firstRate
            if(firstValue == 0f){
                rootView?.setCurrencyValue(true, "")
            }else {
                rootView?.setCurrencyValue(true, firstValue.toString())
            }
        }
    }

    fun onValueChanged(isFirstCurrency: Boolean, value: String){
        isFirstCurrencyLastChanged = isFirstCurrency
        if(isFirstCurrency){
            firstValue = value.toFloatOrNull() ?: 0f
        }else{
            secondValue = value.toFloatOrNull() ?: 0f
        }
        updateCurrencyValues()
    }

    fun onCurrencyChosen(id: Long){
        rootView?.closeDialog()
        viewModelScope.launch {
            if (whichCurrencyChoosing == 1) {
                if(id == 0L){

                    val currency = java.util.Currency.getAvailableCurrencies().find { it.currencyCode == App.context.getString(R.string.UAH) } ?: return@launch
                    firstRate = 1f
                    rootView?.setupFirstCurrency(
                        App.context.resources.getResourceEntryName(ExtendedCurrency.getCurrencyByISO(currency.currencyCode).flag),
                        currency.currencyCode.toString()
                    )
                    updateCurrencyValues()

                }else {

                    val currency = databaseManager.getCurrency(id)
                    firstRate = currency.firstRate.toFloat()
                    rootView?.setupFirstCurrency(
                        currency.flag,
                        currency.symbol
                    )
                    updateCurrencyValues()

                }
            } else if (whichCurrencyChoosing == 2) {
                if(id == 0L){

                    val currency = java.util.Currency.getAvailableCurrencies().find { it.currencyCode == App.context.getString(R.string.UAH) } ?: return@launch
                    secondRate = 1f
                    rootView?.setupSecondCurrency(
                        App.context.resources.getResourceEntryName(ExtendedCurrency.getCurrencyByISO(currency.currencyCode).flag),
                        currency.currencyCode.toString()
                    )
                    updateCurrencyValues()

                }else {

                    val currency = databaseManager.getCurrency(id)
                    secondRate = currency.secondRate.toFloat()
                    rootView?.setupSecondCurrency(
                        currency.flag,
                        currency.symbol
                    )
                    updateCurrencyValues()

                }
            }
            whichCurrencyChoosing = 0
        }
    }

    fun onFragmentDestroyed(){
        myCompositeDisposable?.clear()
    }

    fun onBackClicked(){
        rootView?.hideKeyboard()
        rootView?.openLastFragment()
    }

    private var whichCurrencyChoosing = 0
    fun onFirstCurrencyClicked(){
        whichCurrencyChoosing = 1
        rootView?.showDialog()
    }

    fun onSecondCurrencyClicked(){
        whichCurrencyChoosing = 2
        rootView?.showDialog()
    }
}