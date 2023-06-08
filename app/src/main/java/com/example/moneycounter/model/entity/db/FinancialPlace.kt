package com.example.moneycounter.model.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneycounter.model.db.DBConfig

@Entity(tableName = DBConfig.FinancialPlace.TABLE_NAME)
data class FinancialPlace(
    @ColumnInfo(name = DBConfig.Category.Columns.TITLE) val title: String,
    @ColumnInfo(name = DBConfig.Category.Columns.ICON) val icon: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DBConfig.Finance.Columns.ID) val id: Long = 0
)
