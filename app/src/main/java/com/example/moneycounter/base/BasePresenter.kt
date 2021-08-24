package com.example.moneycounter.base

import com.example.moneycounter.features.intro.IntroContract

abstract class BasePresenter<T: BaseContract> {

    protected var rootView: T? = null

    fun attachView(view: T) {
        rootView = view
        onViewAttached()
    }

    open fun onViewAttached() {}
}