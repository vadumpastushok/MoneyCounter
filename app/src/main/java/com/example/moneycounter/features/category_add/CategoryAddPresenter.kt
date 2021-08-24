package com.example.moneycounter.features.category_add

import androidx.navigation.NavController
import com.example.moneycounter.NavGraphDirections
import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.entity.ui.MoneyType

class CategoryAddPresenter: BasePresenter<CategoryAddContract>() {

    override fun onViewAttached() {

    }




    companion object {
        fun start(navController: NavController, moneyType: MoneyType) {
            val direction = NavGraphDirections.actionToCategoryAdd(moneyType)
            navController.navigate(direction)
        }
    }

}