package com.example.moneycounter.model.db

import androidx.room.*
import com.example.moneycounter.model.entity.db.Currency
import com.example.moneycounter.model.entity.db.Finance
import com.example.moneycounter.model.entity.db.FinancialPlace

@Dao
interface FinancialPlaceDao {

    @Query("SELECT * FROM ${DBConfig.FinancialPlace.TABLE_NAME}")
    suspend fun getAllFinancialPlaces() : MutableList<FinancialPlace>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFinancialPlace(financialPlace: FinancialPlace)

    @Delete
    suspend fun deleteFinancialPlace(financialPlace: FinancialPlace)
}