package com.example.moneycounter.features.category

import android.view.View


interface CategoryTouchAdapter {

    fun onItemSelected(itemView : View)

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemClear(itemView : View)
}
