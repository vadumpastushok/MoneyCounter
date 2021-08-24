package com.example.moneycounter.model.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneycounter.model.db.DBConfig

@Entity(tableName = DBConfig.Finance.TABLE_NAME)
data class Finance(
    @ColumnInfo(name = DBConfig.Finance.Columns.CATEGORY_ID) val category_id: Long,
    @ColumnInfo(name = DBConfig.Finance.Columns.AMOUNT) val amount: Int,
    @ColumnInfo(name = DBConfig.Finance.Columns.DATE) val date: Long,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DBConfig.Finance.Columns.ID) val id: Long = 0
)