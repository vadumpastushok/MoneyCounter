package com.example.moneycounter.model.db

import com.example.moneycounter.app.App
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.CategoryWithFinances
import com.example.moneycounter.model.entity.db.Currency
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType
import javax.inject.Inject

class DatabaseManager @Inject constructor(
    private val categoryDao: CategoryDao,
    private val financeDao: FinanceDao,
    private val currencyDao: CurrencyDao
) {

    suspend fun getCategoryByType(type: MoneyType): MutableList<Category> = categoryDao.getCategoryByType(type)

    suspend fun getCategoryById(id: Long) : Category = categoryDao.getCategoryById(id)

    suspend fun getMoneyTypeById(id: Long): MoneyType = categoryDao.getMoneyTypeById(id)


    suspend fun getCategoryWithFinancesByMoneyType(moneyType: MoneyType): MutableList<CategoryWithFinances> =
        categoryDao.getCategoryWithFinancesByMoneyType(moneyType)

    suspend fun getCategoryWithFinances(): List<CategoryWithFinances> = categoryDao.getCategoryWithFinances()

    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)

    suspend fun insertCategory(categories: MutableList<Category>)  = categoryDao.insertCategory(categories)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    suspend fun getNumberOfCategoriesByMoneyType(moneyType: MoneyType): Int = categoryDao.getNumberOfCategoriesByMoneyType(moneyType)


    suspend fun getAllFinances(): MutableList<Finance> = financeDao.getAllFinances()

    suspend fun getFinancesByCategoryId(id: Long): MutableList<Finance> = financeDao.getFinancesByCategoryId(id)

    suspend fun insertFinance(finance: Finance) = financeDao.insertFinance(finance)

    suspend fun deleteFinance(finances: MutableList<Finance>) = financeDao.deleteFinance(finances)


    suspend fun getAllCurrencies() = currencyDao.getAllCurrencies()

    suspend fun getCurrency(id: Long) = currencyDao.getCurrency(id)

    suspend fun insertCurrencies(currencies: MutableList<Currency>) = currencyDao.insertCurrency(currencies)


    fun deleteAll(){
        App.context.deleteDatabase(DBConfig.DB_NAME)
    }

}