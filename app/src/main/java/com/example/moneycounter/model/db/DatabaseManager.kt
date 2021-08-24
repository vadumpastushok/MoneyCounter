package com.example.moneycounter.model.db

import com.example.moneycounter.app.App
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.CategoryWithFinances
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType

class DatabaseManager(
    private val categoryDao: CategoryDao,
    private val financeDao: FinanceDao
) {

     suspend fun getCategoryByType(type: MoneyType): MutableList<Category> = categoryDao.getCategoryByType(type)

    suspend fun getCategoryById(id: Long) : Category = categoryDao.getCategoryById(id)

    suspend fun getMoneyTypeById(id: Long): MoneyType = categoryDao.getMoneyTypeById(id)

    suspend fun getCategoryIcons(): MutableList<String> = categoryDao.getCategoryIcons().toMutableSet().toMutableList()


    suspend fun getCategoryWithFinancesByMoneyType(moneyType: MoneyType): MutableList<CategoryWithFinances> =
        categoryDao.getCategoryWithFinancesByMoneyType(moneyType)

    suspend fun getCategoryWithFinances(): List<CategoryWithFinances> = categoryDao.getCategoryWithFinances()

    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)

    suspend fun insertCategory(categories: MutableList<Category>)  = categoryDao.insertCategory(categories)

    suspend fun updateCategory(categories: MutableList<Category>) = categoryDao.updateCategory(categories)

    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    suspend fun deleteCategoriesByMoneyType(moneyType: MoneyType) = categoryDao.deleteCategoriesByMoneyType(moneyType)

    suspend fun getNumberOfCategoriesByMoneyType(moneyType: MoneyType): Int = categoryDao.getNumberOfCategoriesByMoneyType(moneyType)


    suspend fun getAllFinance(): MutableList<Finance> = financeDao.getAllFinance()

    suspend fun getFinancesByCategoryId(id: Long): MutableList<Finance> = financeDao.getFinancesByCategoryId(id)

    suspend fun insertFinance(finance: Finance) = financeDao.insertFinance(finance)

    fun deleteAll(){
        App.context.deleteDatabase(DBConfig.DB_NAME)
    }

}