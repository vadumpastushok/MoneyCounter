package com.example.moneycounter.model.api

import com.example.moneycounter.model.entity.api.ApiCurrency
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET(Api.CURRENCY)
    fun getCurrency() : Observable<List<ApiCurrency>>
}