package com.example.moneycounter.model.entity.api

data class ApiCurrency(
    var currencyCodeA: Int,
    var currencyCodeB: Int,
    var date: Long,
    var rateSell: Float = 0f,
    var rateBuy: Float = 0f,
    var rateCross: Float
    )
