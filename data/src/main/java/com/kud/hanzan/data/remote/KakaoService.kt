package com.kud.hanzan.data.remote

import com.kud.hanzan.data.entity.place.CategoryResult
import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import com.kud.hanzan.data.entity.place.RoadAddressResult
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {
    // 키워드로 장소 검색
    @GET("v2/local/search/keyword.json?category_group_code=FD6")
    suspend fun getKeywordPlace(@Query("query") keyword: String) : PlaceKeywordResult

    // 현재 위치 기반으로 한 주소 반환
    @GET("/v2/local/geo/coord2address")
    suspend fun getRoadAddress(@Query("x") longitude: String, @Query("y") latitude: String) : RoadAddressResult

    // 현재 위치 주위 음식점 및 술집 찾기
    @GET("v2/local/search/category.json?category_group_code=FD6&radius=10000&sort=distance&")
    suspend fun getCategoryPlace(@Query("x") longitude: String, @Query("y") latitude: String, @Query("page") page: Int) : CategoryResult
}