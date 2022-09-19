package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import com.kud.hanzan.domain.model.Place
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {
    @GET("v2/local/search/keyword.json?category_group_code=FD6")
    suspend fun getKeywordPlace(@Query("query") keyword: String) : PlaceKeywordResult
}