package com.example.moneycounter.model.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneycounter.model.entity.db.*
import com.example.moneycounter.model.entity.ui.MoneyType
import javax.inject.Inject

class DatabaseManager @Inject constructor(
    private val categoryDao: CategoryDao,
    private val financeDao: FinanceDao,
    private val currencyDao: CurrencyDao,
    private val financialPlaceDao: FinancialPlaceDao,
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

    suspend fun deleteAllCategories() = categoryDao.deleteAllCategories()

    suspend fun getNumberOfCategoriesByMoneyType(moneyType: MoneyType): Int = categoryDao.getNumberOfCategoriesByMoneyType(moneyType)



    suspend fun getAllFinances(): MutableList<Finance> = financeDao.getAllFinances()

    suspend fun getFinancesByCategoryId(id: Long): MutableList<Finance> = financeDao.getFinancesByCategoryId(id)

    suspend fun insertFinance(finance: Finance) = financeDao.insertFinance(finance)

    suspend fun deleteFinance(finances: MutableList<Finance>) = financeDao.deleteFinance(finances)

    suspend fun deleteAllFinances() = financeDao.deleteAllFinances()


    suspend fun getAllCurrencies() = currencyDao.getAllCurrencies()

    suspend fun getCurrency(id: Long) = currencyDao.getCurrency(id)

    suspend fun insertCurrencies(currencies: MutableList<Currency>) = currencyDao.insertCurrency(currencies)

    suspend fun deleteAllCurrencies() = currencyDao.deleteAllCurrencies()


    suspend fun getAllFinancialPlaces() = financialPlaceDao.getAllFinancialPlaces()

    suspend fun insertFinancialPlace(financialPlace: FinancialPlace) = financialPlaceDao.insertFinancialPlace(financialPlace)

    suspend fun deleteFinancialPlace(financialPlace: FinancialPlace) = financialPlaceDao.deleteFinancialPlace(financialPlace)

}