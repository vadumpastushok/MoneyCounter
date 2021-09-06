package com.example.moneycounter.features.write_to_us

import com.example.moneycounter.base.BasePresenter

class WriteToUsPresenter: BasePresenter<WriteToUsContract>() {

    fun editIsEmpty(isEmpty: Boolean){
        rootView?.setEditEnabled(!isEmpty)
    }

    fun onBackClicked(){
        rootView?.hideKeyboard()
        rootView?.openLastFragment()
    }

    fun onNextButtonClicked(){
        rootView?.openHomeFragment()
    }

}