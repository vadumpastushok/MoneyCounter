package com.example.moneycounter.features.input_amount

import com.example.moneycounter.base.BaseContract
import com.example.moneycounter.base.BasePresenter

class InputAmountPresenter: BasePresenter<InputAmountContract>() {

    fun onBackButtonClicked(){
        rootView?.hideKeyboard()
        rootView?.openLastFragment()
    }

    fun onInputAmountButtonClicked(){
        rootView?.hideKeyboard()
        rootView?.openHomeFragment()
    }
}