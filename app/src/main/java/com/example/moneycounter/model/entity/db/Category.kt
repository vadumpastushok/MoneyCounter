package com.example.moneycounter.model.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneycounter.model.db.DBConfig
import com.example.moneycounter.model.entity.ui.MoneyType

@Entity(tableName = DBConfig.Category.TABLE_NAME)
data class Category(
    @ColumnInfo(name = DBConfig.Category.Columns.TITLE) val title: String,
    @ColumnInfo(name = DBConfig.Category.Columns.ICON) val icon: String,
    @ColumnInfo(name = DBConfig.Category.Columns.COLOR) val color: Int,
    @ColumnInfo(name = DBConfig.Category.Columns.TYPE) val type: MoneyType,
    @ColumnInfo(name = DBConfig.Category.Columns.ORDER) var order: Int,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DBConfig.Category.Columns.ID) val id: Long = 0
)