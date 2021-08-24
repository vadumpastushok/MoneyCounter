package com.example.moneycounter.model.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.entity.ui.CurrencyType

@Entity(tableName = DBConfig.Currency.TABLE_NAME)
data class Currency(
    @ColumnInfo(name = DBConfig.Currency.Columns.NAME) var name: String,
    @ColumnInfo(name = DBConfig.Currency.Columns.SYMBOL) var symbol: String,
    @ColumnInfo(name = DBConfig.Currency.Columns.FLAG) var flag: String,
    @ColumnInfo(name = DBConfig.Currency.Columns.FIRST_RATE) var firstRate: String,
    @ColumnInfo(name = DBConfig.Currency.Columns.SECOND_RATE) var secondRate: String,
    @ColumnInfo(name = DBConfig.Currency.Columns.TYPE) var type: CurrencyType,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DBConfig.Currency.Columns.ID) val id: Long = 0
)