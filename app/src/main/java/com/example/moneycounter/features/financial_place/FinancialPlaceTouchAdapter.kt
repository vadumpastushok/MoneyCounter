package com.example.moneycounter.features.financial_place

import android.view.View


interface FinancialPlaceTouchAdapter {

    fun onItemSelected(itemView : View)

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemClear(itemView : View)
}
