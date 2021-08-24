package com.example.moneycounter.features.graph

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.GraphEntity
import com.example.moneycounter.model.entity.ui.MoneyType

interface GraphContract: BaseContract {
    fun setupLineChart(moneyType: MoneyType, data: MutableMap<Long, GraphEntity?>)
}