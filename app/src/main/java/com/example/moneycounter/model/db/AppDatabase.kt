package com.example.moneycounter.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneycounter.model.entity.db.Category
import com.example.moneycounter.model.entity.db.Currency
import com.example.moneycounter.model.entity.db.Finance

@Database(entities = [
    Category::class,
    Finance::class,
    Currency::class
], version = DBConfig.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun financeDao(): FinanceDao
    abstract fun currencyDao(): CurrencyDao
}