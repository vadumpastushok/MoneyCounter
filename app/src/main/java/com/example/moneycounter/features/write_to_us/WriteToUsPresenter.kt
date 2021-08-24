package com.example.moneycounter.features.write_to_us

import com.example.moneycounter.base.BasePresenter

class WriteToUsPresenter: BasePresenter<WriteToUsContract>() {

    override fun onViewAttached() {
    }

    fun editIsEmpty(isEmpty: Boolean){
        rootView?.setEditEnabled(!isEmpty)
    }

    fun onBackClicked(){
        rootView?.openLastFragment()
    }

    fun onNextButtonClicked(){
        rootView?.openHomeFragment()
    }

}