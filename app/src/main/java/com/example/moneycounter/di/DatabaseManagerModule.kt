package com.example.moneycounter.di

import android.content.Context
import androidx.room.Room
import com.example.moneycounter.model.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseManagerModule {

    @Provides
    fun provideDB(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, DBConfig.DB_NAME
        ).build()
    }

    @Provides
    fun provideCategoryDao(
        db: AppDatabase
    ): CategoryDao{
        return db.categoryDao()
    }

    @Provides
    fun provideFinanceDao(
        db: AppDatabase
    ): FinanceDao{
        return db.financeDao()
    }

    @Provides
    fun provideCurrencyDao(
        db: AppDatabase
    ): CurrencyDao{
        return db.currencyDao()
    }

    @Provides
    fun provideDatabaseManager(
        categoryDao: CategoryDao,
        financeDao: FinanceDao,
        currencyDao: CurrencyDao
    ): DatabaseManager{
        return DatabaseManager(
            categoryDao,
            financeDao,
            currencyDao
        )
    }
}