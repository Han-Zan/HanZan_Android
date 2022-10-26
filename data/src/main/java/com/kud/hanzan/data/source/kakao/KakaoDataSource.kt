package com.kud.hanzan.data.source.kakao

import com.kud.hanzan.data.entity.place.CategoryResult
import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import com.kud.hanzan.data.entity.place.RoadAddressResult
import kotlinx.coroutines.flow.Flow

interface KakaoDataSource {
    fun getKeywordPlace(keyword: String) : Flow<PlaceKeywordResult>
    fun getRoadAddress(longitude: String, latitude: String) : Flow<RoadAddressResult>
    fun getCategoryPlace(longitude: String, latitude: String,page: Int) : Flow<CategoryResult>
}