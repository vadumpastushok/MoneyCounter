package com.example.moneycounter.features.category

import com.example.moneycounter.base.BasePresenter
import com.example.moneycounter.model.entity.MoneyType

class CategoryPresenter: BasePresenter<CategoryContract>() {

    override fun onViewAttached() {
        if(rootView?.getMoneyType() == MoneyType.INCOME)rootView?.setupIncome()
        else rootView?.setupCosts()
    }

    fun onCategorySelected(id: Int){
        rootView?.openInputAmountFragment(id)
    }

    fun onCancel(){
        rootView?.openLastFragment()
    }

}