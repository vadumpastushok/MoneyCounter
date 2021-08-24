package com.example.moneycounter.features.currency

import androidx.recyclerview.widget.RecyclerView
import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.model.entity.db.Currency

interface CurrencyContract: BaseContract {

    fun openLastFragment()

    fun setupRecycleView(data: MutableList<Currency>)

    fun getRecycleView() : RecyclerView

    fun showMessage(message: String)
}