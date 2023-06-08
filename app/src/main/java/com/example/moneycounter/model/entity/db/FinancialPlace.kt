package com.example.moneycounter.model.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneycounter.model.db.DBConfig

@Entity(tableName = DBConfig.FinancialPlace.TABLE_NAME)
data class FinancialPlace(
    @ColumnInfo(name = DBConfig.FinancialPlace.Columns.TITLE) val title: String,
    @ColumnInfo(name = DBConfig.FinancialPlace.Columns.ICON) val icon: String,
    @ColumnInfo(name = DBConfig.FinancialPlace.Columns.COLOR) val color: Int,
    @ColumnInfo(name = DBConfig.FinancialPlace.Columns.ORDER) var order: Int,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DBConfig.FinancialPlace.Columns.ID) val id: Long = 0
)
