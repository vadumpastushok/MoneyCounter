package com.example.moneycounter.model.db

import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.CategoryWithFinances
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType

class DatabaseManager(
    private val categoryDao: CategoryDao,
    private val financeDao: FinanceDao
) {

    /**
     * Category
     */

    suspend fun getAllCategory(): List<Category> = categoryDao.getAllCategory()

    suspend fun getCategoryByType(type: MoneyType): MutableList<Category> = categoryDao.getCategoryByType(type)

    suspend fun getCategoryById(id: Long) : Category = categoryDao.getCategoryById(id)

    suspend fun getMoneyTypeById(id: Long): MoneyType = categoryDao.getMoneyTypeById(id)

    suspend fun getCategoryWithFinancesByMoneyType(moneyType: MoneyType): List<CategoryWithFinances> =
        categoryDao.getCategoryWithFinancesByMoneyType(moneyType)

    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)

    suspend fun insertCategory(categories: MutableList<Category>)  = categoryDao.insertCategory(categories)

    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)

    suspend fun updateCategory(categories: MutableList<Category>) = categoryDao.updateCategory(categories)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)


    suspend fun getAllFinance(): MutableList<Finance> = financeDao.getAllFinance()

    //suspend fun getRecentFinance(date: Long): MutableList<Finance> = financeDao.getRecentFinance(date)

    suspend fun insertFinance(finance: Finance) = financeDao.insertFinance(finance)

    suspend fun deleteFinance(finance: Finance) = financeDao.deleteFinance(finance)
}