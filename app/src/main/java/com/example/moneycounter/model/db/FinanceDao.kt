package com.example.moneycounter.model.db

import androidx.room.*
import com.example.moneycounter.model.entity.db.Finance

@Dao
interface FinanceDao {

    @Query("SELECT * FROM ${DBConfig.Finance.TABLE_NAME}")
    suspend fun getAllFinances(): MutableList<Finance>

    @Query("SELECT * FROM ${DBConfig.Finance.TABLE_NAME} WHERE ${DBConfig.Finance.Columns.CATEGORY_ID} == :id")
    suspend fun getFinancesByCategoryId(id: Long): MutableList<Finance>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinance(finance: Finance)

    @Delete
    suspend fun deleteFinance(finances: MutableList<Finance>)
}