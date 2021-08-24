package com.example.moneycounter.features.category

interface CategoryTouchAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
}