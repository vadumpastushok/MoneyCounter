package com.example.moneycounter.base

import androidx.lifecycle.ViewModel

abstract class BasePresenter<T: BaseContract>: ViewModel() {

    protected var rootView: T? = null

    fun attachView(view: T) {
        rootView = view
        onViewAttached()
    }

    open fun onViewAttached() {}
}