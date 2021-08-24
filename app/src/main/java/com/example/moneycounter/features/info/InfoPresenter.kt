package com.example.moneycounter.features.info

import com.example.moneycounter.base.BasePresenter

class InfoPresenter: BasePresenter<InfoContract>() {

    fun onBackPressed(){
        rootView?.openLastFragment()
    }

}