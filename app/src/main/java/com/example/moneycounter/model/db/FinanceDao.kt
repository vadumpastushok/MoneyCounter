package com.example.moneycounter.model.db

import androidx.room.*
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.ui.MoneyType

@Dao
interface FinanceDao {

    @Query("SELECT * FROM ${DBConfig.Finance.TABLE_NAME}")
    suspend fun getAllFinance(): MutableList<Finance>

    //@Query("SELECT * FROM ${DBConfig.Finance.TABLE_NAME}")
    //suspend fun getRecentFinance(date: Long): MutableList<Finance>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinance(finance: Finance)

    @Delete
    suspend fun deleteFinance(finance: Finance)
}