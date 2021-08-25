package com.example.moneycounter.features.info

import com.example.moneycounter.base.BasePresenter
import javax.inject.Inject

class InfoPresenter @Inject constructor(): BasePresenter<InfoContract>() {

    fun onBackPressed(){
        rootView?.openLastFragment()
    }

}