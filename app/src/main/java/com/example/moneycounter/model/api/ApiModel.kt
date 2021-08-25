package com.example.moneycounter.model.api

import com.example.moneycounter.model.entity.api.ApiCurrency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiModel(private val apiService: ApiService) {

    fun getCurrency(): Observable<List<ApiCurrency>> {
        return apiService.getCurrency().applySchedulers()
    }

    private fun <T>Observable<T>.applySchedulers(): Observable<T> {
        return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}