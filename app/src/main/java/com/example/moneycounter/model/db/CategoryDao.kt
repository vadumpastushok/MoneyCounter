package com.example.moneycounter.model.db

import androidx.room.*
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.CategoryWithFinances
import com.example.moneycounter.model.entity.ui.MoneyType

@Dao
interface CategoryDao {

    @Query("SELECT * FROM ${DBConfig.Category.TABLE_NAME} WHERE :type LIKE ${DBConfig.Category.Columns.TYPE}")
    suspend fun getCategoryByType(type: MoneyType): MutableList<Category>

    @Query("SELECT * FROM ${DBConfig.Category.TABLE_NAME} WHERE :id ==  ${DBConfig.Category.Columns.ID}")
    suspend fun getCategoryById(id: Long) : Category

    @Query("SELECT type FROM ${DBConfig.Category.TABLE_NAME} WHERE :id ==  ${DBConfig.Category.Columns.ID}")
    suspend fun getMoneyTypeById(id: Long) : MoneyType

    @Transaction
    @Query("SELECT * FROM ${DBConfig.Category.TABLE_NAME} WHERE :moneyType == ${DBConfig.Category.Columns.TYPE}")
    suspend fun getCategoryWithFinancesByMoneyType(moneyType: MoneyType): MutableList<CategoryWithFinances>

    @Transaction
    @Query("SELECT * FROM ${DBConfig.Category.TABLE_NAME}")
    suspend fun getCategoryWithFinances(): List<CategoryWithFinances>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categories: MutableList<Category>)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM ${DBConfig.Category.TABLE_NAME}")
    suspend fun deleteAllCategories()

    @Query("SELECT COUNT(${DBConfig.Category.Columns.TYPE}) FROM ${DBConfig.Category.TABLE_NAME} WHERE :moneyType == ${DBConfig.Category.Columns.TYPE}")
    suspend fun getNumberOfCategoriesByMoneyType(moneyType: MoneyType): Int
}