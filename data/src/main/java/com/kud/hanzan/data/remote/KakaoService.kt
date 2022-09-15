package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.place.PlaceResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {
    @GET("v2/local/search/keyword.json")
    fun getKeywordPlace(@Query("query") keyword: String) : PlaceResult
}