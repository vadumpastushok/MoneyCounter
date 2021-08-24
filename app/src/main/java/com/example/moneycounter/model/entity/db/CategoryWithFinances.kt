package com.example.moneycounter.model.entity.db

import androidx.room.Embedded
import androidx.room.Relation
import com.example.moneycounter.model.db.DBConfig

data class CategoryWithFinances (
    @Embedded val category: Category,
    @Relation(
        parentColumn = DBConfig.Category.Columns.ID,
        entityColumn = DBConfig.Finance.Columns.CATEGORY_ID
    )
    val finances: List<Finance>
)