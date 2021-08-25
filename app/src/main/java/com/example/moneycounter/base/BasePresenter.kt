package com.example.moneycounter.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T: BaseContract>: ViewModel() {

    protected var rootView: T? = null

    protected val myCompositeDisposable = CompositeDisposable()

    fun attachView(view: T) {
        rootView = view
        onViewAttached()
    }

    open fun onViewAttached() {}
}