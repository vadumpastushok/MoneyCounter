package com.example.moneycounter.model.entity.db

import com.example.moneycounter.model.entity.ui.MoneyType

data class GraphEntity(
    val amount: Int,
    val date: Long,
    val moneyType: MoneyType
)