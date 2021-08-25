package com.example.moneycounter.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneycounter.model.entity.db.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM ${DBConfig.Currency.TABLE_NAME}")
    suspend fun getAllCurrencies() : MutableList<Currency>

    @Query("SELECT * FROM ${DBConfig.Currency.TABLE_NAME} WHERE :id == ${DBConfig.Currency.Columns.ID}")
    suspend fun getCurrency(id: Long) : Currency

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencies: MutableList<Currency>)

}